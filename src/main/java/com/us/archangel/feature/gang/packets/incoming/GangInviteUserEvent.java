package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.commands.GangInviteUserCommand;

public class GangInviteUserEvent extends MessageHandler {
    @Override
    public void handle() {
        String targetedUsername = this.packet.readString();

        if (targetedUsername == null) {
            return;
        }

        new GangInviteUserCommand().handle(this.client, new String[] {null, targetedUsername});
    }
}