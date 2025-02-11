package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.corp.commands.CorpStopWorkCommand;
import com.us.archangel.feature.corp.packets.outgoing.CorpListComposer;

public class CorpListEvent extends MessageHandler {

    @Override
    public void handle() {
        this.client.sendResponse(new CorpListComposer());
    }
}