package com.us.archangel.feature.police.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.police.commands.WantedListRemoveUserCommand;

public class WantedListRemoveUserEvent extends MessageHandler {
    @Override
    public void handle() {
        String username = this.packet.readString();
        if (username == null) {
            return;
        }
        new WantedListRemoveUserCommand().handle(this.client,new String[] {null, username});
    }
}