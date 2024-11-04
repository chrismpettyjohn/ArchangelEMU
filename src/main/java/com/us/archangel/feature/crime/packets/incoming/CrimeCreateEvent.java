package com.us.archangel.feature.crime.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.crime.entity.CrimeEntity;
import com.us.archangel.crime.mapper.CrimeMapper;
import com.us.archangel.crime.model.CrimePermissions;
import com.us.archangel.crime.service.CrimeService;
import com.us.archangel.feature.crime.packets.outgoing.CrimeDataComposer;

public class CrimeCreateEvent extends MessageHandler {

    @Override
    public void handle() throws Exception {
        boolean canCreateCrimes = this.client.getHabbo().hasPermissionRight(CrimePermissions.CREATE);

        if (!canCreateCrimes) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        String displayName = this.packet.readString();
        String description = this.packet.readString();
        int jailTime = this.packet.readInt();

        if (displayName == null || description == null || jailTime <= 0) {
            return;
        }

        CrimeEntity crimeEntity = new CrimeEntity();
        crimeEntity.setDisplayName(displayName);
        crimeEntity.setDescription(description);
        crimeEntity.setJailTimeSeconds(jailTime);

        CrimeService.getInstance().create(CrimeMapper.toModel(crimeEntity));

        this.client.sendResponse(new CrimeDataComposer(CrimeMapper.toModel(crimeEntity)));
    }

}
