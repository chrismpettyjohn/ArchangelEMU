package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.commands.GangInviteAcceptCommand;

public class GangInviteAcceptEvent extends MessageHandler {
    @Override
    public void handle() {
        String targetedUsername = this.packet.readString();

        if (targetedUsername == null) {
            return;
        }

        new GangInviteAcceptCommand().handle(this.client, new String[] {null, targetedUsername});
    }
}