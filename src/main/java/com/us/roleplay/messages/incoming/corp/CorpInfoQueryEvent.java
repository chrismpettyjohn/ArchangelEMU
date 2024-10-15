package com.us.roleplay.messages.incoming.corp;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.messages.outgoing.corp.CorpInfoComposer;

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