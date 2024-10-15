package com.us.archangel.feature.police.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.police.packets.outgoing.WantedListComposer;

public class WantedListEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new WantedListComposer());
    }
}