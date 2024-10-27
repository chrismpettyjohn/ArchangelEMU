package com.us.archangel.feature.corp.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.us.archangel.corp.model.CorpInviteModel;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpInviteService;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;

public class CorpAcceptJobCommand extends Command {
    public CorpAcceptJobCommand() {
        super("cmd_corp_acceptjob");
    }
    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params == null) {
            return true;
        }

        int corpId = Integer.parseInt(params[1]);
        CorpModel targetedCorp = CorpService.getInstance().getById(corpId);

        if (targetedCorp == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.corp_not_found"));
            return true;
        }

        CorpInviteModel corpInvite = CorpInviteService.getInstance().getByCorpAndUserId(corpId, gameClient.getHabbo().getHabboInfo().getId());

        if (corpInvite == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_corp_invite_missing"));
            return true;
        }

        gameClient.getHabbo().getPlayer().setCorpId(corpInvite.getCorpId());
        gameClient.getHabbo().getPlayer().setCorpRoleId(corpInvite.getCorpRoleId());

        CorpRoleService.getInstance().deleteById(corpInvite.getId());

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_corp_invite_accepted").replace(":corp", targetedCorp.getDisplayName()));

        return true;
    }
}