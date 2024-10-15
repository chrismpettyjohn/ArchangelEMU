package com.us.roleplay.messages.incoming.license;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.license.commands.LicenseConnectComputerCommand;

public class LicenseConnectComputerEvent extends MessageHandler {
    @Override
    public void handle() {
        String itemID = String.valueOf(this.packet.readInt());
        String corpID = String.valueOf(this.packet.readInt());

        if (itemID == null || corpID == null) {
            return;
        }

        new LicenseConnectComputerCommand().handle(this.client, new String[] {null, itemID, corpID});
    }
}