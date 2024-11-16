package com.us.nova.feature.user.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.user.packets.outgoing.UserGuestbookQueryListComposer;

public class UserGuestbookQueryListEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new UserGuestbookQueryListComposer(this.packet.readInt()));
    }
}