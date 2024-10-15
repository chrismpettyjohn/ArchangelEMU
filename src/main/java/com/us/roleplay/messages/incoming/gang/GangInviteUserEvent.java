package com.us.roleplay.messages.incoming.gang;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.commands.gang.GangInviteUserCommand;

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