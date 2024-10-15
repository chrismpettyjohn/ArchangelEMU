package com.us.archangel.feature.license.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.license.packets.outgoing.LicenseAgencyListComposer;

public class LicenseAgencyListEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new LicenseAgencyListComposer());
    }
}