package com.us.archangel.feature.player.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.player.packets.outgoing.UserQueryListComposer;

public class UserQueryListEvent extends MessageHandler {
    @Override
    public void handle() {
        int page = this.packet.readInt();
        String query = this.packet.readString();
        this.client.sendResponse(new UserQueryListComposer(page, query));
    }
}