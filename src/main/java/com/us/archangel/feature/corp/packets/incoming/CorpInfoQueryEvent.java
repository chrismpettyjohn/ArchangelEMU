package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.corp.packets.outgoing.CorpInfoComposer;

public class CorpInfoQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        Integer corpID = this.packet.readInt();

        if (corpID == 0) {
            return;
        }

        this.client.sendResponse(new CorpInfoComposer(corpID));
    }

}