package com.us.nova.feature.security.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.security.packets.outgoing.CurrentEmailComposer;

public class QueryEmailEvent extends MessageHandler {

    @Override
    public void handle() {
        this.client.sendResponse(new CurrentEmailComposer(this.client.getHabbo(), false));
    }

}