package com.us.archangel.feature.taxi.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.taxi.packets.outgoing.TaxiFeeComposer;

public class TaxiFeeQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new TaxiFeeComposer());

    }
}