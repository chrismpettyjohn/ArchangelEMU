package com.us.archangel.feature.hotbar.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.hotbar.packets.outgoing.HotBarListItemsComposer;

public class HotBarListItemsEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new HotBarListItemsComposer(this.client.getHabbo()));
    }
}