package com.us.archangel.feature.room.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.room.packets.outgoing.RoomQueryListComposer;

public class RoomQueryListEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new RoomQueryListComposer(this.client.getHabbo()));
    }
}
