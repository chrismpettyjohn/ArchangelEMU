package com.us.archangel.feature.corp.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.us.roleplay.corp.Corp;
import com.us.roleplay.corp.CorpManager;
import com.us.roleplay.corp.CorpTag;

import java.util.List;

public class CorpQuitJobCommand extends Command {
    public CorpQuitJobCommand() {
        super("cmd_corp_quitjob");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        List<Corp> welfareCorps = CorpManager.getInstance().getCorpsByTag(CorpTag.WELFARE);

        if (welfareCorps.isEmpty()) {
            throw new RuntimeException("no welfare corp found");
        }

        Corp welfareCorp = welfareCorps.get(0);

        if (gameClient.getHabbo().getHabboRoleplayStats().getCorp().getGuild().getId() == welfareCorp.getGuild().getId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_quitjob_unemployed"));
            return true;
        }

        gameClient.getHabbo().getHabboRoleplayStats().setCorp(welfareCorp.getGuild().getId(), welfareCorp.getPositionByOrderID(1).getId());

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_quitjob_success"));

        return true;
    }
}