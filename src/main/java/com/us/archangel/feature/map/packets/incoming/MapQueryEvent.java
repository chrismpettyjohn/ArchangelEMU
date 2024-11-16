package com.us.archangel.feature.map.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.map.packets.outgoing.MapQueryComposer;

public class MapQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new MapQueryComposer(this.client.getHabbo()));
    }
}
