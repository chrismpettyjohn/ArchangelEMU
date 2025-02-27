package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;
import com.us.archangel.feature.corp.packets.outgoing.CorpPositionListComposer;

import java.util.List;

public class CorpDeletePositionEvent extends MessageHandler {

    @Override
    public void handle() {
        int corpPositionID = this.packet.readInt();

        CorpRoleModel corpPosition = CorpRoleService.getInstance().getById(corpPositionID);


        if (corpPosition == null) {
            return;
        }

        CorpModel corp = CorpService.getInstance().getById(corpPosition.getCorpId());

        if (corp == null) {
            return;
        }

        if (corpPosition.getId() == this.client.getHabbo().getPlayer().getCorpRoleId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.corp_position.cant_delete_your_job"));
            return;
        }

        if (corp.getUserId() != this.client.getHabbo().getHabboInfo().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.corp.not_the_owner"));
            return;
        }

        List<CorpRoleModel> corpRoleModels = CorpRoleService.getInstance().findManyByCorpId(corp.getId());

        if (corpRoleModels.size() == 1) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.cant_delete_last_role"));
            return;
        }

        List<CorpModel> welfareCorps = CorpService.getInstance().findManyByIndustry(CorpIndustry.PublicAid);

        if (welfareCorps.isEmpty()) {
            throw new RuntimeException("no welfare corp");
        }

        CorpModel welfareCorp = welfareCorps.get(0);
        CorpRoleModel welfarePosition = CorpRoleService.getInstance().getByCorpAndOrderId(welfareCorp.getId(), 1);

        if (welfarePosition == null) {
            throw new RuntimeException("no welfare position");
        }

        List<PlayerModel> habbosInPosition = PlayerService.getInstance().getByCorpRoleID(corpPositionID);

        for (PlayerModel habboStats : habbosInPosition) {
            habboStats.setCorpId(welfareCorp.getId());
            habboStats.setCorpRoleId(welfarePosition.getId());
        }

        CorpRoleService.getInstance().deleteById(corpPositionID);

        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.corp_position.delete_success")
                .replace(":position", corpPosition.getDisplayName())
        );

        this.client.sendResponse(new CorpPositionListComposer(corp));
    }

}