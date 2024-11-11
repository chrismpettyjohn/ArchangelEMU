package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.corp.commands.CorpStopWorkCommand;

public class CorpStopWorkEvent extends MessageHandler {
    @Override
    public void handle() {
        new CorpStopWorkCommand().handle(this.client, new String[] {});
    }
}