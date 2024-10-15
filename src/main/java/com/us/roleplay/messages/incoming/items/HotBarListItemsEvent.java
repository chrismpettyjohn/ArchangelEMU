package com.us.roleplay.messages.incoming.items;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.messages.outgoing.items.HotBarListItemsComposer;

public class HotBarListItemsEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new HotBarListItemsComposer(this.client.getHabbo()));
    }
}