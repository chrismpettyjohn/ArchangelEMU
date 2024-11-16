package com.us.archangel.feature.corp.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.corp.actions.WorkShiftAction;

public class CorpStartWorkCommand extends Command {
    public CorpStartWorkCommand() {
        super("cmd_corp_startwork");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (gameClient.getHabbo().getPlayer().isWorking()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_start_work_user_is_already_working"));
            return true;
        }

        CorpModel userEmployer = CorpService.getInstance().getById(gameClient.getHabbo().getPlayer().getCorp().getId());

        if (userEmployer == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_start_work_company_does_not_exist"));
            return true;
        }

        CorpRoleModel userRole = CorpRoleService.getInstance().getById(gameClient.getHabbo().getPlayer().getCorpRole().getId());

        if (userRole == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_start_work_position_does_not_exist"));
            return true;
        }

        if (!userRole.isCanWorkAnywhere() && gameClient.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId() != userEmployer.getRoomId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_start_work_not_in_boundaries"));
            return true;
        }

        Emulator.getThreading().run(new WorkShiftAction(gameClient.getHabbo()));

        return true;
    }
}