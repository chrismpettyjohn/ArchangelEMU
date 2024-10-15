package com.us.roleplay.messages.incoming.bank;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.bank.commands.BankAccountLookupCommand;

public class BankAccountQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        String corpID = String.valueOf(this.packet.readInt());
        String username = this.packet.readString();

        if (corpID == null || username == null) {
            return;
        }

        new BankAccountLookupCommand().handle(this.client, new String[] {null, corpID, username});
    }
}