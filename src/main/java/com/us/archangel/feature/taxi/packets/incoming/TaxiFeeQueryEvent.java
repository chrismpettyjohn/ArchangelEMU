package com.us.archangel.feature.taxi.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.taxi.packets.outgoing.TaxiStandComposer;

public class TaxiFeeQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new TaxiStandComposer());

    }
}