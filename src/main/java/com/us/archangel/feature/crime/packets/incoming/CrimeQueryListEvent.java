package com.us.archangel.feature.crime.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.crime.model.CrimePermissions;
import com.us.archangel.feature.crime.packets.outgoing.CrimeListComposer;

public class CrimeQueryListEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canViewCrimes = this.client.getHabbo().hasPermissionRight(CrimePermissions.READ);

        if (!canViewCrimes) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        this.client.sendResponse(new CrimeListComposer());
    }
}
