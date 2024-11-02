package com.us.nova.feature.betacode.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.betacode.packets.outgoing.BetaCodeListComposer;

public class BetaCodeQueryListEvent extends MessageHandler {

    @Override
    public void handle() {
        this.client.sendResponse(new BetaCodeListComposer());
    }

}