package com.us.nova.core;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.nova.feature.notification.packets.outgoing.NotificationComposer;

import java.util.Collection;

public class NotificationHelper {

    public static void notifyOnline(String message) {
        Collection<Habbo> onlineUsers = Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values();

        for (Habbo habbo : onlineUsers) {
            habbo.getClient().sendResponse(new NotificationComposer(message));
        }
    }

    public static void notifyRoom(int roomId, String message) {
        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(roomId);

        if (room == null) {
            return;
        }

        Collection<Habbo> roomUsers = room.getRoomUnitManager().getCurrentHabbos().values();

        for (Habbo habbo : roomUsers) {
            habbo.getClient().sendResponse(new NotificationComposer(message));
        }
    }

}
