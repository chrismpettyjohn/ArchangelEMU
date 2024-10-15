package com.us.archangel.feature.police.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.police.commands.CallPoliceCommand;

public class CallPoliceEvent extends MessageHandler {
    @Override
    public void handle() {
        String message = this.packet.readString();

        if (message == null) {
            return;
        }

        new CallPoliceCommand().handle(this.client,new String[] {null, message});
    }
}