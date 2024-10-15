package com.us.roleplay.messages.incoming.corp;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.messages.outgoing.corp.CorpListComposer;

public class CorpListEvent extends MessageHandler {

    @Override
    public void handle() {
        this.client.sendResponse(new CorpListComposer());
    }
}