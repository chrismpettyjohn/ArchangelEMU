package com.us.archangel.feature.crime.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.crime.entity.CrimeEntity;
import com.us.archangel.crime.mapper.CrimeMapper;
import com.us.archangel.crime.model.CrimePermissions;
import com.us.archangel.crime.service.CrimeService;
import com.us.archangel.feature.crime.packets.outgoing.CrimeListComposer;

public class CrimeUpdateEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canUpdateCrimes = this.client.getHabbo().hasPermissionRight(CrimePermissions.UPDATE);

        if (!canUpdateCrimes) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        int crimeId = this.packet.readInt();
        String displayName = this.packet.readString();
        String description= this.packet.readString();
        int jailTime = this.packet.readInt();

        CrimeEntity crimeEntity = CrimeMapper.toEntity(CrimeService.getInstance().getById(crimeId));
        crimeEntity.setDisplayName(displayName);
        crimeEntity.setDescription(description);
        crimeEntity.setJailTimeSeconds(jailTime);

        CrimeService.getInstance().update(crimeId, CrimeMapper.toModel(crimeEntity));

        this.client.sendResponse(new CrimeListComposer());
    }
}