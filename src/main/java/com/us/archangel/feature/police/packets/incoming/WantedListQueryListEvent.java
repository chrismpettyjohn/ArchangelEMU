package com.us.archangel.feature.police.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.police.packets.outgoing.WantedListQueryListComposer;

public class WantedListQueryListEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new WantedListQueryListComposer());
    }
}