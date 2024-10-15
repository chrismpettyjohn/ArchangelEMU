package com.us.roleplay.messages.incoming.license;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.messages.outgoing.license.LicenseAgencyListComposer;

public class LicenseAgencyListEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new LicenseAgencyListComposer());
    }
}