package com.us.archangel.feature.bank.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.bank.packets.outgoing.BankListComposer;

public class BankListEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new BankListComposer());
    }
}