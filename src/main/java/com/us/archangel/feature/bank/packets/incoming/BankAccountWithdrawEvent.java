package com.us.archangel.feature.bank.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.bank.commands.BankAccountWithdrawCommand;

public class BankAccountWithdrawEvent extends MessageHandler {
    @Override
    public void handle() {
        String corpID = String.valueOf(this.packet.readInt());
        String withdrawAmount = String.valueOf(this.packet.readInt());

        if (corpID == null || withdrawAmount == null) {
            return;
        }

        new BankAccountWithdrawCommand().handle(this.client, new String[] {null, corpID, withdrawAmount});
    }
}