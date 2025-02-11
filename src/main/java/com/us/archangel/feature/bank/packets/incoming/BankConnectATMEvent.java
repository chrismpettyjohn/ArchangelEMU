package com.us.archangel.feature.bank.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.bank.commands.BankConnectATMCommand;

public class BankConnectATMEvent extends MessageHandler {
    @Override
    public void handle() {
        String itemID = String.valueOf(this.packet.readInt());
        String corpID = String.valueOf(this.packet.readInt());

        if (itemID == null || corpID == null) {
            return;
        }

        new BankConnectATMCommand().handle(this.client, new String[] {null, itemID, corpID});
    }
}