package com.us.archangel.feature.police.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.police.packets.outgoing.PoliceListReportsComposer;

public class PoliceListReportsEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new PoliceListReportsComposer(this.client.getHabbo()));
    }
}