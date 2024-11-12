package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.enums.CorpSector;
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

        corp.setDisplayName(this.packet.readString());
        corp.setDescription(this.packet.readString());
        corp.setUserId(this.packet.readInt());
        corp.setRoomId(this.packet.readInt());
        corp.setSector(CorpSector.fromString(this.packet.readString()));
        corp.setIndustry(CorpIndustry.fromString(this.packet.readString()));

        CorpService.getInstance().update(corp.getId(), corp);

        this.client.sendResponse(new CorpInfoComposer(corp.getId()));
    }

}
