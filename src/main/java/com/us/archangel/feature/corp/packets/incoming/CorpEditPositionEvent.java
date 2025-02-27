package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.mapper.CorpRoleMapper;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.corp.commands.CorpStartWorkCommand;
import com.us.archangel.feature.corp.packets.outgoing.CorpEmployeeListComposer;
import com.us.archangel.feature.corp.packets.outgoing.CorpPositionInfoComposer;
import com.us.archangel.feature.corp.packets.outgoing.CorpPositionInfoQueryEvent;
import com.us.archangel.feature.corp.packets.outgoing.CorpPositionListComposer;

public class CorpEditPositionEvent extends MessageHandler {

    @Override
    public void handle() {
        int corpPositionID = this.packet.readInt();

        CorpRoleModel corpPosition = CorpRoleService.getInstance().getById(corpPositionID);

        if (corpPosition == null) {
            return;
        }

        CorpModel corp = CorpService.getInstance().getById(corpPosition.getCorpId());

        if (corp.getUserId() != this.client.getHabbo().getHabboInfo().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.corp.not_the_owner"));
            return;
        }

        corpPosition.setOrderId(this.packet.readInt());
        corpPosition.setDisplayName(this.packet.readString());
        corpPosition.setMotto(this.packet.readString());
        corpPosition.setSalary(this.packet.readInt());
        corpPosition.setMaleFigure(this.packet.readString());
        corpPosition.setFemaleFigure(this.packet.readString());
        corpPosition.setCanHire((this.packet.readBoolean()));
        corpPosition.setCanFire((this.packet.readBoolean()));
        corpPosition.setCanPromote((this.packet.readBoolean()));
        corpPosition.setCanDemote((this.packet.readBoolean()));
        corpPosition.setCanWorkAnywhere((this.packet.readBoolean()));
        corpPosition.update();

        this.client.getHabbo().whisper(Emulator.getTexts()
                .getValue("roleplay.corp_position.edit_success")
                .replace(":position", corpPosition.getDisplayName())
        );

        this.client.sendResponse(new CorpPositionListComposer(corp));
        this.client.sendResponse(new CorpPositionInfoComposer(corpPosition.getId()));
    }

}