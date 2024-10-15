package com.us.archangel.feature.time.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.time.packets.outgoing.TimeOfDayComposer;

public class TimeOfDayQueryEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new TimeOfDayComposer());

    }
}