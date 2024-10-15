package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.commands.corp.CorpAcceptJobCommand;

public class CorpAcceptJobEvent extends MessageHandler {
    @Override
    public void handle() {
        String corpName = this.packet.readString();

        if (corpName == null) {
            return;
        }

        new CorpAcceptJobCommand().handle(this.client, new String[] {null, corpName});
    }
}