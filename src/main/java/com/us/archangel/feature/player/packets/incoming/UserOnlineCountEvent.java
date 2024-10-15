package com.us.archangel.feature.player.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.player.packets.outgoing.UserOnlineCountComposer;

public class UserOnlineCountEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new UserOnlineCountComposer());
    }
}