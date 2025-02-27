package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.entity.CorpRoleEntity;
import com.us.archangel.corp.mapper.CorpRoleMapper;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.corp.packets.outgoing.CorpPositionInfoComposer;
import com.us.archangel.feature.corp.packets.outgoing.CorpPositionListComposer;

import java.util.List;

public class CorpCreatePositionEvent extends MessageHandler {

    @Override
    public void handle() {
        int corpID = this.packet.readInt();
        String name = this.packet.readString();
        String motto = this.packet.readString();
        String description = this.packet.readString();
        int salary = this.packet.readInt();
        String maleFigure = this.packet.readString();
        String femaleFigure = this.packet.readString();
        boolean canHire = this.packet.readBoolean();
        boolean canFire = this.packet.readBoolean();
        boolean canPromote = this.packet.readBoolean();
        boolean canDemote = this.packet.readBoolean();
        boolean canWorkAnywhere = this.packet.readBoolean();

        CorpModel corp = CorpService.getInstance().getById(corpID);

        if (corp == null) {
            return;
        }

        if (corp.getUserId() != this.client.getHabbo().getHabboInfo().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.corp.not_the_owner"));
            return;
        }

        List<CorpRoleModel> corpRoleList = CorpRoleService.getInstance().findManyByCorpId(corpID);

        CorpRoleModel corpRoleModel = new CorpRoleModel();
        corpRoleModel.setCorpId(corpID);
        corpRoleModel.setOrderId(corpRoleList.size() + 1);
        corpRoleModel.setDisplayName(name);
        corpRoleModel.setDescription(description);
        corpRoleModel.setMotto(motto);
        corpRoleModel.setSalary(salary);
        corpRoleModel.setMaleFigure(maleFigure);
        corpRoleModel.setFemaleFigure(femaleFigure);
        corpRoleModel.setCanHire(canHire);
        corpRoleModel.setCanFire(canFire);
        corpRoleModel.setCanPromote(canPromote);
        corpRoleModel.setCanDemote(canDemote);
        corpRoleModel.setCanWorkAnywhere(canWorkAnywhere);

        CorpRoleModel savedCorpRoleModel = CorpRoleService.getInstance().create(corpRoleModel);

        this.client.getHabbo().whisper(Emulator.getTexts()
                .getValue("roleplay.corp_position.create_success")
                .replace(":position", name)
        );

        this.client.sendResponse(new CorpPositionInfoComposer(savedCorpRoleModel.getId()));
        this.client.sendResponse(new CorpPositionListComposer(corp));
    }
}
