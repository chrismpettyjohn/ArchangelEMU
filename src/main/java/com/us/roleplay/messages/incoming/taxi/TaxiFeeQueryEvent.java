package com.us.roleplay.messages.incoming.taxi;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.messages.outgoing.taxi.TaxiFeeComposer;

public class TaxiFeeQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new TaxiFeeComposer());

    }
}