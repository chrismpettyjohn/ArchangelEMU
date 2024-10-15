package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangInfoComposer;

public class GangInfoQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        Integer gangID = this.packet.readInt();

        if (gangID == null) {
            return;
        }

        this.client.sendResponse(new GangInfoComposer(gangID));
    }
}