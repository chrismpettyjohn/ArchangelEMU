package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.corp.commands.CorpOfferUserJobCommand;

public class CorpOfferUserJobEvent extends MessageHandler {
    @Override
    public void handle() {
        String targetedUsername = this.packet.readString();

        if (targetedUsername == null) {
            return;
        }

        new CorpOfferUserJobCommand().handle(this.client, new String[] {null, targetedUsername});
    }
}