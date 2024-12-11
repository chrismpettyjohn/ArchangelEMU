package com.us.archangel.feature.map.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.map.packets.outgoing.MapQueryComposer;

public class MapQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        int roomId = this.packet.readInt();
        boolean includeConnections = this.packet.readBoolean();
        
        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(roomId);
        if (room == null) return;
        
        this.client.sendResponse(new MapQueryComposer(room));
    }
}