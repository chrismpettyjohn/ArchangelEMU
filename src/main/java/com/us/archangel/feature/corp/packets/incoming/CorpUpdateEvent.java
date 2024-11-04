package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.enums.CorpSector;
import com.us.archangel.corp.mapper.CorpMapper;
import com.us.archangel.corp.model.CorpPermissions;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.corp.packets.outgoing.CorpInfoComposer;

public class CorpUpdateEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canUpdateCorps = this.client.getHabbo().hasPermissionRight(CorpPermissions.UPDATE);

        if (!canUpdateCorps) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        CorpEntity corpEntity = CorpMapper.toEntity(CorpService.getInstance().getById(this.packet.readInt()));
        corpEntity.setDisplayName(this.packet.readString());
        corpEntity.setDescription(this.packet.readString());
        corpEntity.setIndustry(CorpIndustry.fromString(this.packet.readString()));
        corpEntity.setRoomId(this.packet.readInt());
        corpEntity.setSector(CorpSector.fromString(this.packet.readString()));
        corpEntity.setUserId(this.packet.readInt());

        CorpService.getInstance().update(corpEntity.getId(), CorpMapper.toModel(corpEntity));

        this.client.sendResponse(new CorpInfoComposer(corpEntity.getId()));
    }
}
