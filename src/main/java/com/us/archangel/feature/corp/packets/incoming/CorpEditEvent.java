package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.enums.CorpSector;
import com.us.archangel.corp.mapper.CorpMapper;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.corp.packets.outgoing.CorpInfoComposer;

public class CorpEditEvent extends MessageHandler {
    @Override
    public void handle() {
        CorpModel corp = CorpService.getInstance().getById(this.packet.readInt());

        if (corp == null) {
            return;
        }

        boolean canEditCorp = corp.getId() == this.client.getHabbo().getHabboInfo().getId() || this.client.getHabbo().hasPermissionRight(Permission.ACC_CORPS_EDIT_ALL);

        if (!canEditCorp) {
            return;
        }

        CorpEntity corpEntity = CorpMapper.toEntity(corp);
        corpEntity.setDisplayName(this.packet.readString());
        corpEntity.setDescription(this.packet.readString());
        corpEntity.setUserId(this.packet.readInt());
        corpEntity.setRoomId(this.packet.readInt());
        corpEntity.setSector(CorpSector.fromString(this.packet.readString()));
        corpEntity.setIndustry(CorpIndustry.fromString(this.packet.readString()));

        CorpService.getInstance().update(corp.getId(), CorpMapper.toModel(corpEntity));

        this.client.sendResponse(new CorpInfoComposer(corp.getId()));
    }

}
