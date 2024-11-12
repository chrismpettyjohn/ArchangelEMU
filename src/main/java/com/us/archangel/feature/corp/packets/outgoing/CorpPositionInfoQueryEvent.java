package com.us.archangel.feature.corp.packets.outgoing;

import com.eu.habbo.messages.incoming.MessageHandler;

public class CorpPositionInfoQueryEvent  extends MessageHandler {
    @Override
    public void handle() {
        int corpPositionID = this.packet.readInt();
        this.client.sendResponse(new CorpPositionInfoComposer(corpPositionID));
    }
}