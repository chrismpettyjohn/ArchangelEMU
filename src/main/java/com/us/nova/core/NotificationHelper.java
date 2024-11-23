package com.us.nova.core;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.us.nova.feature.notification.packets.outgoing.NotificationComposer;

import java.util.Collection;

public class NotificationHelper {

    public static void announceOnline(String message) {
        Collection<Habbo> onlineUsers = Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values();

        for (Habbo habbo : onlineUsers) {
            habbo.getClient().sendResponse(new NotificationComposer(message));
        }
    }

    public static void announceRoom(int roomId, String message) {
        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(roomId);

        if (room == null) {
            return;
        }

        Collection<Habbo> roomUsers = room.getRoomUnitManager().getCurrentHabbos().values();

        for (Habbo habbo : roomUsers) {
            habbo.getClient().sendResponse(new NotificationComposer(message));
        }
    }

    public static void sendOnline(MessageComposer composer) {
        Collection<Habbo> onlineUsers = Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values();

        for (Habbo habbo : onlineUsers) {
            habbo.getClient().sendResponse(composer);
        }
    }

    public static void sendRoom(int roomId, MessageComposer composer) {
        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(roomId);

        if (room == null) {
            return;
        }

        Collection<Habbo> roomUsers = room.getRoomUnitManager().getCurrentHabbos().values();

        for (Habbo habbo : roomUsers) {
            habbo.getClient().sendResponse(composer);
        }
    }

}
