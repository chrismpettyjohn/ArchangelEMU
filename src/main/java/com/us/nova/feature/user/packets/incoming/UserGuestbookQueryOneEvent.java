package com.us.nova.feature.user.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.user.packets.outgoing.UserGuestbookQueryOneComposer;

public class UserGuestbookQueryOneEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new UserGuestbookQueryOneComposer(this.packet.readInt()));
    }
}