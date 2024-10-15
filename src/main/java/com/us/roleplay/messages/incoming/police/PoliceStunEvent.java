package com.us.roleplay.messages.incoming.police;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.commands.police.StunCommand;

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