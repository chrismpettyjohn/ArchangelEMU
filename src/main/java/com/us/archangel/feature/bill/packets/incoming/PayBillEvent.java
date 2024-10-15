package com.us.archangel.feature.bill.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.bill.commands.PayBillCommand;

public class PayBillEvent extends MessageHandler {
    @Override
    public void handle() {
        String billID = this.packet.readString();

        if (billID == null) {
            return;
        }

        new PayBillCommand().handle(this.client, new String[] {null, billID});
    }
}