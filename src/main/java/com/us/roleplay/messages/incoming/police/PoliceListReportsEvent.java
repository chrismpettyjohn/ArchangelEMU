package com.us.roleplay.messages.incoming.police;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.messages.outgoing.police.PoliceListReportsComposer;

public class PoliceListReportsEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new PoliceListReportsComposer(this.client.getHabbo()));
    }
}