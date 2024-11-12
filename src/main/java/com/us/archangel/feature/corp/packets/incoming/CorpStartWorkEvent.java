package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.corp.commands.CorpStartWorkCommand;

public class CorpStartWorkEvent extends MessageHandler {
    @Override
    public void handle() {
        new CorpStartWorkCommand().handle(this.client, new String[] {});
    }
}