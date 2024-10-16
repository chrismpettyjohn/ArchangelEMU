package com.us.archangel.feature.corp.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;

import java.util.List;

public class CorpQuitJobCommand extends Command {
    public CorpQuitJobCommand() {
        super("cmd_corp_quitjob");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        List<CorpModel> welfareCorps = CorpService.getInstance().findManyByIndustry(CorpIndustry.PublicAid);

        if (welfareCorps.isEmpty()) {
            throw new RuntimeException("no welfare corp found");
        }

        CorpModel welfareCorp = welfareCorps.get(0);

        if (gameClient.getHabbo().getHabboRoleplayStats().getCorp().getId() == welfareCorp.getId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_quitjob_unemployed"));
            return true;
        }

        CorpRoleModel welfareRole = CorpRoleService.getInstance().getByCorpAndOrderId(welfareCorp.getId(), 1);

        gameClient.getHabbo().getHabboRoleplayStats().setCorp(welfareCorp.getId(), welfareRole.getId());

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_quitjob_success"));

        return true;
    }
}