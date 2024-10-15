package com.us.roleplay.messages.incoming.taxi;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.taxi.commands.CallTaxiCommand;

public class CallTaxiEvent extends MessageHandler {
    @Override
    public void handle() {
        String roomID = this.packet.readInt().toString();
        new CallTaxiCommand().handle(this.client,new String[] {null, roomID});
    }
}