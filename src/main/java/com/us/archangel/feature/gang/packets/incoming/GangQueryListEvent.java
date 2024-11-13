package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangQueryListComposer;

public class GangQueryListEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new GangQueryListComposer());
    }
}
