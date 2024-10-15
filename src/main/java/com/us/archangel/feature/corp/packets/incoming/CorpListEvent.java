package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.commands.corp.CorpStopWorkCommand;
import com.us.archangel.feature.corp.packets.outgoing.CorpListComposer;

public class CorpListEvent extends MessageHandler {

    @Override
    public void handle() {
        this.client.sendResponse(new CorpListComposer());
    }

    public static class CorpStopWorkEvent extends MessageHandler {
        @Override
        public void handle() {
            new CorpStopWorkCommand().handle(this.client, new String[] {});
        }
    }
}