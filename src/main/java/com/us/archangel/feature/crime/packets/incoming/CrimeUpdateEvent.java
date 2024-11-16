package com.us.archangel.feature.crime.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.police.entity.PoliceCrimeEntity;
import com.us.archangel.police.mapper.PoliceCrimeMapper;
import com.us.archangel.police.service.PoliceCrimeService;
import com.us.archangel.feature.crime.packets.outgoing.CrimeListComposer;

public class CrimeUpdateEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canUpdateCrimes = this.client.getHabbo().hasPermissionRight(Permission.ACC_CRIMES_EDIT_ALL);

        if (!canUpdateCrimes) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        int crimeId = this.packet.readInt();
        String displayName = this.packet.readString();
        String description= this.packet.readString();
        int jailTime = this.packet.readInt();

        PoliceCrimeEntity policeCrimeEntity = PoliceCrimeMapper.toEntity(PoliceCrimeService.getInstance().getById(crimeId));
        policeCrimeEntity.setDisplayName(displayName);
        policeCrimeEntity.setDescription(description);
        policeCrimeEntity.setJailTimeSeconds(jailTime);

        PoliceCrimeService.getInstance().update(crimeId, PoliceCrimeMapper.toModel(policeCrimeEntity));

        this.client.sendResponse(new CrimeListComposer());
    }
}