package com.us.archangel.feature.hospital.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.list.LayCommand;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.feature.hospital.interactions.InteractionHospitalBed;
import com.us.archangel.player.enums.PlayerAction;
import com.us.archangel.room.enums.RoomType;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class TeleportHospitalAction implements Runnable {

    private final Habbo habbo;

    @Override
    public void run() {
        if (this.habbo.getRoomUnit() == null) {
            return;
        }

//        if (this.habbo.getRoomUnit().getRoom().isHospital()) {
//            return;
//        }

        if (this.habbo.getPlayer().getEscortingPlayerId() != null) {
            this.habbo.getPlayer().setCurrentAction(PlayerAction.None);
            this.habbo.getPlayer().setEscortingPlayerId(null);
        }

        this.habbo.shout(Emulator.getTexts().getValue("roleplay.user_is_dead"));
        this.habbo.getRoomUnit().setCanWalk(false);

        int deadTeleportDelay = Emulator.getConfig().getInt("roleplay.dead.delay", 10000);

        new LayCommand().handle(this.habbo.getClient(), new String[0]);

        this.habbo.shout(Emulator.getTexts().getValue("roleplay.dead.teleporting_to_hospital_delay").replace(":seconds", String.valueOf(deadTeleportDelay / 1000)));


        Room hospitalRoom = Emulator.getGameEnvironment().getRoomManager().getActiveRooms().stream().filter(room -> room.isHospital()).findFirst().orElse(null);

        if (hospitalRoom == null) {
            throw new RuntimeException("no hospitals found");
        }

        habbo.goToRoom(hospitalRoom.getRoomInfo().getId(), () -> {
            Collection<RoomItem> hospitalBedItems = hospitalRoom.getRoomItemManager().getItemsOfType(InteractionHospitalBed.class);
            for (RoomItem hospitalBedItem : hospitalBedItems) {
                List<RoomTile> hospitalBedRoomTiles = hospitalBedItem.getOccupyingTiles(hospitalRoom.getLayout());
                RoomTile firstAvailableHospitalBedTile = hospitalBedRoomTiles.isEmpty() ? null : hospitalBedRoomTiles.get(0);
                if (firstAvailableHospitalBedTile == null) {
                    return;
                }
                habbo.getRoomUnit().setLocation(firstAvailableHospitalBedTile);
                break;
            }
        });
    }
}
