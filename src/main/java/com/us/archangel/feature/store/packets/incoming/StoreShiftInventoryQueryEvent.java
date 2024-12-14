package com.us.archangel.feature.store.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.store.packets.outgoing.StoreShiftInventoryDataComposer;

public class StoreShiftInventoryQueryEvent extends MessageHandler {
    @Override
    public void handle () {
      this.client.sendResponse(new StoreShiftInventoryDataComposer(this.client.getHabbo()));
    }
}
