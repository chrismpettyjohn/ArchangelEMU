package com.us.roleplay.messages.incoming.police;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.police.commands.EscortCommand;

public class PoliceEscortEvent extends MessageHandler {
    @Override
    public void handle() {
        String targetedUsername = this.packet.readString();

        if (targetedUsername == null) {
            return;
        }

        new EscortCommand().handle(this.client,new String[] {null, targetedUsername});
    }
}