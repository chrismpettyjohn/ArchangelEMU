package com.us.archangel.feature.crime.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.police.service.PoliceCrimeService;
import com.us.archangel.feature.crime.packets.outgoing.CrimeListComposer;

public class CrimeDeleteEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canDeleteCrimes = this.client.getHabbo().hasPermissionRight(Permission.ACC_CRIMES_EDIT_ALL);

        if (!canDeleteCrimes) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        PoliceCrimeService.getInstance().deleteById(this.packet.readInt());

        this.client.sendResponse(new CrimeListComposer());
    }

}