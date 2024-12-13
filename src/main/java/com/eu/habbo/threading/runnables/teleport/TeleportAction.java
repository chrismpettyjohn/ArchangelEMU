package com.eu.habbo.threading.runnables.teleport;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.interactions.InteractionTeleport;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.constants.RoomUnitStatus;
import com.eu.habbo.habbohotel.rooms.entities.RoomRotation;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.messages.outgoing.rooms.users.UserUpdateComposer;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class TeleportAction implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeleportAction.class);

    private final RoomItem currentTeleport;
    private final Room room;
    private final GameClient client;

    @Override
    public void run() {
        synchronized (this.room) {
            if (this.client.getHabbo().getRoomUnit().getRoom() != this.room) return;

            this.client.getHabbo().getRoomUnit().removeStatus(RoomUnitStatus.MOVE);
            this.room.sendComposer(new UserUpdateComposer(this.client.getHabbo().getRoomUnit()).compose());
        }

        InteractionTeleport resolvedTeleport = (InteractionTeleport) this.currentTeleport;

        if (resolvedTeleport.getTargetRoomId() == 0) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.teleport.no_connect"));
            this.client.getHabbo().getRoomUnit().setCanWalk(true);
            this.client.getHabbo().getRoomUnit().setTeleporting(false);
            this.client.getHabbo().getRoomUnit().setLeavingTeleporter(false);
            resolvedTeleport.setExtraData("0");
            this.room.updateItemState(this.currentTeleport);
            return;
        }


        proceedToTargetRoom(resolvedTeleport);
    }

    private void proceedToTargetRoom(InteractionTeleport teleport) {
        synchronized (this.room) {
            if (this.currentTeleport.getRoomId() != teleport.getTargetRoomId()) {
                Room targetRoom = Emulator.getGameEnvironment().getRoomManager().getRoom(teleport.getTargetRoomId());
                if (targetRoom == null) {
                    return;
                }

                if (targetRoom.isPreLoaded()) {
                    targetRoom.loadData();
                }

                RoomItem targetTeleport = targetRoom.getRoomItemManager().getRoomItemById(teleport.getTargetId());
                if (targetTeleport == null) {
                    this.proceedToTeleportCompletion(null);
                    return;
                }

                RoomTile teleportLocation = targetRoom.getLayout().getTile(targetTeleport.getCurrentPosition().getX(), targetTeleport.getCurrentPosition().getY());
                if (teleportLocation == null) {
                    this.proceedToTeleportCompletion(targetTeleport);
                    return;
                }

                RoomTile targetTile = this.room.getLayout().getTile(this.currentTeleport.getCurrentPosition().getX(), this.currentTeleport.getCurrentPosition().getY());
                if (!this.client.getHabbo().getRoomUnit().getCurrentPosition().equals(targetTile)) {
                    this.client.getHabbo().getRoomUnit().setLocation(teleportLocation);
                    this.client.getHabbo().getRoomUnit().getPath().clear();
                    this.client.getHabbo().getRoomUnit().removeStatus(RoomUnitStatus.MOVE);
                    this.client.getHabbo().getRoomUnit().setCurrentZ(teleportLocation.getStackHeight());
                }

                if (targetRoom != this.room) {
                    Room oldRoom = this.room;
                    oldRoom.getRoomUnitManager().removeHabbo(this.client.getHabbo(), true);
                    
                    Emulator.getThreading().run(() -> {
                        Emulator.getGameEnvironment().getRoomManager().enterRoom(
                            this.client.getHabbo(), 
                            targetRoom.getRoomInfo().getId(), 
                            "", 
                            Emulator.getConfig().getBoolean("hotel.teleport.locked.allowed"), 
                            teleportLocation
                        );
                    }, 100);
                }

                this.client.getHabbo().getRoomUnit().setRotation(RoomRotation.values()[targetTeleport.getRotation() % 8]);
                targetTeleport.setExtraData("1");
                targetRoom.updateItem(targetTeleport);
                this.client.getHabbo().getRoomUnit().setRoom(targetRoom);

                Emulator.getThreading().run(() -> proceedToTeleportCompletion(targetTeleport), 25);
            }
        }
    }

    private void proceedToTeleportCompletion(RoomItem targetTeleport) {
        synchronized (this.room) {
            this.client.getHabbo().getRoomUnit().setCanWalk(true);
            this.client.getHabbo().getRoomUnit().setLeavingTeleporter(true);

            this.client.getHabbo().getRoomUnit().setLeavingTeleporter(false);
            this.client.getHabbo().getRoomUnit().setTeleporting(false);
            this.client.getHabbo().getRoomUnit().setCanWalk(true);

            if (targetTeleport != null) {
                targetTeleport.setExtraData("0");
            }
        }
    }
}