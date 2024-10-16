package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.corp.commands.CorpFireUserCommand;
import com.us.roleplay.database.HabboRoleplayStatsRepository;
import com.us.archangel.feature.corp.packets.outgoing.CorpPositionInfoComposer;
import com.us.archangel.feature.corp.packets.outgoing.CorpPositionListComposer;
import com.us.roleplay.users.HabboRoleplayStats;

import java.util.List;

public class CorpDeletePositionEvent extends MessageHandler {

    @Override
    public void handle() {
        int corpID = this.packet.readInt();
        int corpPositionID = this.packet.readInt();

        CorpModel corp = CorpService.getInstance().getById(corpID);

        if (corp == null) {
            return;
        }

        CorpRoleModel corpPosition = CorpRoleService.getInstance().getById(corpPositionID);

        if (corpPosition == null) {
            return;
        }

        if (corpPosition.getId() == this.client.getHabbo().getHabboRoleplayStats().getCorpPosition().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.corp_position.cant_delete_your_job"));
            return;
        }

        if (corp.getUserId() != this.client.getHabbo().getHabboInfo().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.cor.not_the_owner"));
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

        List<HabboRoleplayStats> habbosInPosition = HabboRoleplayStatsRepository.getInstance().getByCorpAndPositionID(corpID, corpPositionID);

        for (HabboRoleplayStats habboStats : habbosInPosition) {
            habboStats.setCorp(welfareCorp.getId(), welfarePosition.getId());
        }

        CorpRoleService.getInstance().deleteById(corpPositionID);

        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.corp_position.delete_success")
                .replace(":position", corpPosition.getName())
        );

        this.client.sendResponse(new CorpPositionListComposer(corp));
    }

    public static class CorpPositionInfoQueryEvent  extends MessageHandler {
        @Override
        public void handle() {
            Integer corpID = this.packet.readInt();
            Integer corpPositionID = this.packet.readInt();

            if (corpID == 0 || corpPositionID == null) {
                return;
            }

            this.client.sendResponse(new CorpPositionInfoComposer(corpID, corpPositionID));
        }
    }

    public static class CorpFireUserEvent extends MessageHandler {
        @Override
        public void handle() {
            String targetedUsername = this.packet.readString();

            if (targetedUsername == null) {
                return;
            }

            new CorpFireUserCommand().handle(this.client, new String[] {null, targetedUsername});
        }
    }
}