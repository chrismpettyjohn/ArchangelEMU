package com.us.archangel.feature.corp.packets.outgoing;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.corp.commands.CorpFireUserCommand;

public class CorpFireUserEvent extends MessageHandler {
    @Override
    public void handle() {
        String targetedUsername = this.packet.readString();

        if (targetedUsername == null) {
            return;
        }

        new CorpFireUserCommand().handle(this.client, new String[] {null, targetedUsername});
    }
}