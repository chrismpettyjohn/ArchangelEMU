package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;

public class GangDeclineInviteEvent extends MessageHandler {
    @Override
    public void handle() {
        String targetedUsername = this.packet.readString();

        if (targetedUsername == null) {
            return;
        }
    }
}