package com.us.archangel.feature.gang.action;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.interactions.InteractionGuildFurni;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.items.entities.RoomItem;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.feature.gang.packets.outgoing.TurfCaptureTimeLeftComposer;

public class CaptureTurfAction implements Runnable {

    public static int CAPTURING_EFFECT_ID = 631;

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

        if (this.room.getRoomInfo().getGuild() != null &&
                this.capturingHabbo.getPlayer().getGang().getId() == this.room.getRoomInfo().getGuild().getId()) {
            this.capturingHabbo.whisper(Emulator.getTexts().getValue("roleplay.turf_capture.already_owned"));
            return;
        }

        this.capturingHabbo.shout(Emulator.getTexts().getValue("roleplay.turf_capture.start"));
        this.capturingHabbo.getRoomUnit().giveEffect(CAPTURING_EFFECT_ID, -1);
        this.room.getRoomTurfManager().startCapturing(this.capturingHabbo.getHabboInfo().getId());

        isRunning = true;
        Emulator.getThreading().run(this, room.getRoomTurfManager().getCaptureFinishesAt() - System.currentTimeMillis());
    }

    public void stop() {
        isRunning = false;
        this.room.getRoomTurfManager().stopCapturing();
        this.room.sendComposer(new TurfCaptureTimeLeftComposer(this.room).compose());
    }

    @Override
    public void run() {
        if (!isRunning) return;

        if (this.capturingHabbo.getRoomUnit().getRoom() != this.room ||
                this.capturingHabbo.getPlayer().isDead()) {
            stop();
            return;
        }

        if (!isWithinCaptureRange()) {
            this.room.getRoomTurfManager().pauseCapturing();
            return;
        } else {
            this.room.getRoomTurfManager().resumeCapturing();
        }

        boolean isContested = false;
        for (Habbo user : this.room.getRoomUnitManager().getCurrentHabbos().values()) {
            if (user.getHabboInfo().getId() != this.capturingHabbo.getHabboInfo().getId() && (user.getPlayer().getGang() == null ||
                    user.getPlayer().getGang() != this.capturingHabbo.getPlayer().getGang())) {
                isContested = true;
                break;
            }
        }

        if (isContested) {
            this.room.getRoomTurfManager().stopCapturing();
            return;
        }

        boolean hasCaptureTimeLeft = System.currentTimeMillis() < this.room.getRoomTurfManager().getCaptureFinishesAt();

        if (hasCaptureTimeLeft) {
            this.room.setNeedsUpdate(true);
            for (RoomItem item : this.room.getRoomItemManager().getCurrentItems().values()) {
                if (item.getBaseItem().getInteractionType().getClass().isAssignableFrom(InteractionGuildFurni.class)) {
                    ((InteractionGuildFurni) item).setGuildId(this.capturingHabbo.getPlayer().getGang().getId());
                }
            }
            this.capturingHabbo.shout(Emulator.getTexts().getValue("roleplay.turf_capture.success"));
            this.room.getRoomTurfManager().stopCapturing();
        }

        this.room.sendComposer(new TurfCaptureTimeLeftComposer(this.room).compose());
    }

    private boolean isWithinCaptureRange() {
        return Math.abs(this.capturingHabbo.getRoomUnit().getCurrentPosition().getX() - this.roomTile.getX()) <= 4 &&
                Math.abs(this.capturingHabbo.getRoomUnit().getCurrentPosition().getY() - this.roomTile.getY()) <= 4;
    }
}