package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.model.CorpPermissions;

public class CorpQueryListEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canReadCorps = this.client.getHabbo().hasPermissionRight(CorpPermissions.READ);

        if (!canReadCorps) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }
    }
}
