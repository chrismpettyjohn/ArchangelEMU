package com.us.archangel.room;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.us.archangel.feature.gang.packets.outgoing.TurfCaptureTimeLeftComposer;
import com.us.nova.core.NotificationHelper;
import lombok.Getter;

@Getter
public class RoomTurfManager {

    private static final int CAPTURE_TIME_SECONDS = Emulator.getConfig().getInt("roleplay.gang.turf_capture_seconds");

    private final int roomId;

    private int capturingUserId;
    private boolean isCapturing;
    private long captureFinishesAt;
    private long capturePausedAt;


    public RoomTurfManager(int roomId) {
        this.roomId = roomId;
    }

    public void startCapturing(int capturingUserId) {
        this.capturingUserId = capturingUserId;
        this.isCapturing = true;
        this.captureFinishesAt = System.currentTimeMillis() + (CAPTURE_TIME_SECONDS * 1000L);
        this.capturePausedAt = 0;
        notifyRoom();
    }

    public void pauseCapturing() {
        if (isCapturing) {
            this.isCapturing = false;
            this.capturePausedAt = System.currentTimeMillis();
            notifyRoom();
        }
    }

    public void resumeCapturing() {
        if (!isCapturing && capturingUserId != 0 && capturePausedAt > 0) {
            this.isCapturing = true;
            long captureTimeLeft = captureFinishesAt - capturePausedAt;
            this.captureFinishesAt = System.currentTimeMillis() + captureTimeLeft;
            this.capturePausedAt = 0;
            notifyRoom();
        }
    }

    public void stopCapturing() {
        this.capturingUserId = 0;
        this.isCapturing = false;
        this.captureFinishesAt = 0;
        this.capturePausedAt = 0;
        notifyRoom();
    }

    public long getCaptureFinishesAt() {
        return this.captureFinishesAt;
    }

    private void notifyRoom() {
        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(this.roomId);
        NotificationHelper.sendRoom(this.roomId, new TurfCaptureTimeLeftComposer(room));
    }
}