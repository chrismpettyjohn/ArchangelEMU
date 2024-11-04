package com.us.archangel.feature.crime.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.crime.model.CrimeModel;
import com.us.archangel.crime.model.CrimePermissions;
import com.us.archangel.crime.service.CrimeService;
import com.us.archangel.feature.crime.packets.outgoing.CrimeDataComposer;

public class CrimeQueryOneEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canViewCrimes = this.client.getHabbo().hasPermissionRight(CrimePermissions.READ);

        if (!canViewCrimes) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        CrimeModel crimeModel = CrimeService.getInstance().getById(this.packet.readInt());

        this.client.sendResponse(new CrimeDataComposer(crimeModel));
    }
}
