package com.us.archangel.feature.gang.action;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.interactions.InteractionGuildFurni;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.feature.gang.packets.outgoing.TurfCaptureTimeLeftComposer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CaptureTurfAction {

    public static int CAPTURING_EFFECT_ID = 631;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final Room room;
    private final RoomTile roomTile;
    private final Habbo capturingHabbo;
    private volatile boolean isRunning;

    public CaptureTurfAction(Room room, RoomTile roomTile, Habbo capturingHabbo) {
        this.room = room;
        this.roomTile = roomTile;
        this.capturingHabbo = capturingHabbo;
        this.isRunning = false;
        this.start();
    }

    public void start() {
        if (isRunning) return;

        if (!this.capturingHabbo.getPlayer().canInteract()) {
            return;
        }

        if (this.capturingHabbo.getPlayer().getGang() == null) {
            this.capturingHabbo.whisper(Emulator.getTexts().getValue("roleplay.turf_capture.no_gang"));
            return;
        }

        if (this.room.getRoomInfo().getGangId() != 0 &&
                this.capturingHabbo.getPlayer().getGang().getId() == this.room.getRoomInfo().getGangId()) {
            this.capturingHabbo.whisper(Emulator.getTexts().getValue("roleplay.turf_capture.already_owned"));
            return;
        }

        this.capturingHabbo.shout(Emulator.getTexts().getValue("roleplay.turf_capture.start"));
        this.capturingHabbo.getRoomUnit().giveEffect(CAPTURING_EFFECT_ID, -1);
        this.room.getRoomTurfManager().startCapturing(this.capturingHabbo.getHabboInfo().getId());

        isRunning = true;
        scheduler.scheduleAtFixedRate(this::checkPosition, 0, 1, TimeUnit.SECONDS);
        long delay = Math.max(1, room.getRoomTurfManager().getCaptureFinishesAt() - System.currentTimeMillis());
        this.scheduler.schedule(this::capture, delay, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        isRunning = false;
        this.capturingHabbo.shout(Emulator.getTexts().getValue("roleplay.turf_capture.stop"));
        this.room.getRoomTurfManager().stopCapturing();
        this.room.sendComposer(new TurfCaptureTimeLeftComposer(this.room).compose());
        scheduler.shutdownNow();
    }

    public void capture() {
        if (!isRunning) return;

        if (this.capturingHabbo.getRoomUnit().getRoom() != this.room ||
                this.capturingHabbo.getPlayer().isDead()) {
            stop();
            return;
        }

        if (isOutsideRange()) {
            this.room.getRoomTurfManager().pauseCapturing();
            return;
        } else {
            this.room.getRoomTurfManager().resumeCapturing();
        }

        // Count gang members and other players
        int gangMembers = 0;
        int otherPlayers = 0;

        for (Habbo user : this.room.getRoomUnitManager().getCurrentHabbos().values()) {
            if (user.getHabboInfo().getId() != this.capturingHabbo.getHabboInfo().getId()) {
                if (user.getPlayer().getGang() != null &&
                        user.getPlayer().getGang().getId() == this.capturingHabbo.getPlayer().getGang().getId()) {
                    gangMembers++;
                } else {
                    otherPlayers++;
                }
            }
        }

        // Pause if there are other players and they outnumber the gang
        if (otherPlayers > 0 && otherPlayers >= gangMembers) {
            this.room.getRoomTurfManager().pauseCapturing();
            return;
        }

        boolean hasCaptureTimeLeft = System.currentTimeMillis() < this.room.getRoomTurfManager().getCaptureFinishesAt();

        if (hasCaptureTimeLeft) {
            for (RoomItem item : this.room.getRoomItemManager().getCurrentItems().values()) {
                if (item.getBaseItem().getInteractionType().getClass().isAssignableFrom(InteractionGuildFurni.class)) {
                    ((InteractionGuildFurni) item).setGuildId(this.capturingHabbo.getPlayer().getGang().getId());
                }
            }
            this.room.getRoomInfo().setGangId(this.capturingHabbo.getPlayer().getGang().getId());
            this.capturingHabbo.shout(Emulator.getTexts().getValue("roleplay.turf_capture.success"));
            this.room.getRoomTurfManager().stopCapturing();
            this.room.setNeedsUpdate(true);
        }

        this.room.sendComposer(new TurfCaptureTimeLeftComposer(this.room).compose());
    }

    private void checkPosition() {
        if (isOutsideRange()) {
            this.capturingHabbo.whisper(Emulator.getTexts().getValue("roleplay.turf_capture.out_of_range"));
            stop();
            return;
        }

        int gangMembers = 0;
        int otherPlayers = 0;

        for (Habbo user : this.room.getRoomUnitManager().getCurrentHabbos().values()) {
            if (user.getHabboInfo().getId() != this.capturingHabbo.getHabboInfo().getId()) {
                if (user.getPlayer().getGang() != null &&
                        user.getPlayer().getGang().getId() == this.capturingHabbo.getPlayer().getGang().getId()) {
                    gangMembers++;
                } else {
                    otherPlayers++;
                }
            }
        }

        if (otherPlayers > 0 && otherPlayers >= gangMembers) {
            this.room.getRoomTurfManager().pauseCapturing();
            this.capturingHabbo.whisper(Emulator.getTexts().getValue("roleplay.turf_capture.outnumbered"));
        }
    }

    private boolean isOutsideRange() {
        return this.capturingHabbo.getRoomUnit().getCurrentPosition() != this.roomTile;
    }
}