package com.us.archangel.feature.map.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.map.packets.outgoing.MapQueryComposer;
import com.us.archangel.room.enums.RoomType;

import java.util.List;

public class MapQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        List<Room> startingRooms = Emulator.getGameEnvironment().getRoomManager().getRoomsByTag(RoomType.MAP_START);

        if (startingRooms.isEmpty()) {
            return;
        }

        this.client.sendResponse(new MapQueryComposer(startingRooms.get(0)));
    }
}
