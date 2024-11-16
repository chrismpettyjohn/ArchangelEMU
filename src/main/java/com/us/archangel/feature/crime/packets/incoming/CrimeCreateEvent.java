package com.us.archangel.feature.crime.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.police.entity.PoliceCrimeEntity;
import com.us.archangel.police.mapper.PoliceCrimeMapper;
import com.us.archangel.police.model.PoliceCrimeModel;
import com.us.archangel.police.service.PoliceCrimeService;
import com.us.archangel.feature.crime.packets.outgoing.CrimeDataComposer;
import com.us.archangel.feature.crime.packets.outgoing.CrimeListComposer;

public class CrimeCreateEvent extends MessageHandler {

    @Override
    public void handle() throws Exception {
        boolean canCreateCrimes = this.client.getHabbo().hasPermissionRight(Permission.ACC_CRIMES_EDIT_ALL);

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

        PoliceCrimeEntity policeCrimeEntity = new PoliceCrimeEntity();
        policeCrimeEntity.setDisplayName(displayName);
        policeCrimeEntity.setDescription(description);
        policeCrimeEntity.setJailTimeSeconds(jailTime);

        PoliceCrimeModel policeCrimeModel = PoliceCrimeMapper.toModel(policeCrimeEntity);
        PoliceCrimeService.getInstance().create(policeCrimeModel);

        this.client.sendResponse(new CrimeDataComposer(policeCrimeModel));
        this.client.sendResponse(new CrimeListComposer());
    }

}
