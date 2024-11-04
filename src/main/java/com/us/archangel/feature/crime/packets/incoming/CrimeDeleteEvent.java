package com.us.archangel.feature.crime.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.crime.model.CrimePermissions;
import com.us.archangel.crime.service.CrimeService;
import com.us.archangel.feature.crime.packets.outgoing.CrimeListComposer;

public class CrimeDeleteEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canDeleteCrimes = this.client.getHabbo().hasPermissionRight(CrimePermissions.DELETE);

        if (!canDeleteCrimes) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        CrimeService.getInstance().deleteById(this.packet.readInt());

        this.client.sendResponse(new CrimeListComposer());
    }

}