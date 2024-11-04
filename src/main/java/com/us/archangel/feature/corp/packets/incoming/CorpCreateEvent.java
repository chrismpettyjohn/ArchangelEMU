package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.enums.CorpSector;
import com.us.archangel.corp.mapper.CorpMapper;
import com.us.archangel.corp.model.CorpPermissions;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.corp.packets.outgoing.CorpListComposer;

public class CorpCreateEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canCreateCorps = this.client.getHabbo().hasPermissionRight(CorpPermissions.CREATE);

        if (!canCreateCorps) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        String displayName = this.packet.readString();
        String description = this.packet.readString();
        CorpIndustry industry = CorpIndustry.fromString(this.packet.readString());
        int roomId = this.packet.readInt();
        CorpSector sector = CorpSector.fromString(this.packet.readString());
        int userID = this.packet.readInt();

        CorpEntity corp = new CorpEntity();
        corp.setDisplayName(displayName);
        corp.setDescription(description);
        corp.setIndustry(industry);
        corp.setRoomId(roomId);
        corp.setSector(sector);
        corp.setUserId(userID);

        CorpService.getInstance().create(CorpMapper.toModel(corp));

        this.client.sendResponse(new CorpListComposer());
    }
}
