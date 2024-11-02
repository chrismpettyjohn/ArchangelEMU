package com.us.nova.feature.bugreport.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.bugreport.packets.outgoing.BugReportListComposer;

public class BugReportQueryListEvent extends MessageHandler {

    @Override
    public void handle() {
        this.client.sendResponse(new BugReportListComposer());
    }

}