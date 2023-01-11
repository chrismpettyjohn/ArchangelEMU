package com.eu.habbo.messages.incoming.navigator;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Rank;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.navigator.GuestRoomSearchResultComposer;
import com.eu.habbo.plugin.events.navigator.NavigatorSearchResultEvent;
import gnu.trove.map.hash.THashMap;

import java.util.ArrayList;
import java.util.List;

public class RoomTextSearchEvent extends MessageHandler {
    public final static THashMap<Rank, THashMap<String, ServerMessage>> cachedResults = new THashMap<>(4);

    @Override
    public void handle() {
        String name = this.packet.readString();

        String prefix = "";
        String query = name;
        List<Room> rooms;

        ServerMessage message = null;
        if (cachedResults.containsKey(this.client.getHabbo().getHabboInfo().getRank())) {
            message = cachedResults.get(this.client.getHabbo().getHabboInfo().getRank()).get((name + "\t" + query).toLowerCase());
        } else {
            cachedResults.put(this.client.getHabbo().getHabboInfo().getRank(), new THashMap<>());
        }

        if (message == null) {
            if (name.startsWith("owner:")) {
                query = name.split("owner:")[1];
                prefix = "owner:";
                rooms = Emulator.getGameEnvironment().getRoomManager().getRoomsForHabbo(name);
            } else if (name.startsWith("tag:")) {
                query = name.split("tag:")[1];
                prefix = "tag:";
                rooms = Emulator.getGameEnvironment().getRoomManager().getRoomsWithTag(name);
            } else if (name.startsWith("group:")) {
                query = name.split("group:")[1];
                prefix = "group:";
                rooms = Emulator.getGameEnvironment().getRoomManager().getGroupRoomsWithName(name);
            } else {
                rooms = Emulator.getGameEnvironment().getRoomManager().getRoomsWithName(name);
            }

            message = new GuestRoomSearchResultComposer(rooms).compose();
            THashMap<String, ServerMessage> map = cachedResults.get(this.client.getHabbo().getHabboInfo().getRank());

            if (map == null) {
                map = new THashMap<>(1);
            }

            map.put((name + "\t" + query).toLowerCase(), message);
            cachedResults.put(this.client.getHabbo().getHabboInfo().getRank(), map);

            NavigatorSearchResultEvent event = new NavigatorSearchResultEvent(this.client.getHabbo(), prefix, query, rooms);
            if (Emulator.getPluginManager().fireEvent(event).isCancelled()) {
                return;
            }
        }

        this.client.sendResponse(message);
    }
}
