package com.us.nova.feature.emusettings.packages.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.user.packets.outgoing.UserGuestbookQueryListComposer;

public class EmuSettingsQueryListEvent extends MessageHandler {

    @Override
    public void handle() {
        this.client.sendResponse(new UserGuestbookQueryListComposer(this.packet.readInt()));
    }

}
