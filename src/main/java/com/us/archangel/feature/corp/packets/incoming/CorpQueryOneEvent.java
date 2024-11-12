package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;

public class CorpQueryOneEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canReadCorps = this.client.getHabbo().hasPermissionRight(Permission.ACC_CORPS_EDIT_ALL);

        if (!canReadCorps) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }
    }
}
