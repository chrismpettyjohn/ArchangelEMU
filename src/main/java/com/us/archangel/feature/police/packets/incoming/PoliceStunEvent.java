package com.us.archangel.feature.police.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.police.commands.StunCommand;

public class PoliceStunEvent extends MessageHandler {
    @Override
    public void handle() {
        String targetedUsername = this.packet.readString();

        if (targetedUsername == null) {
            return;
        }

        new StunCommand().handle(this.client,new String[] {null, targetedUsername});
    }
}