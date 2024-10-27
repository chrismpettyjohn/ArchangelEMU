package com.us.archangel.feature.corp.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.entity.CorpInviteEntity;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpInviteService;
import com.us.archangel.corp.service.CorpRoleService;

public class CorpOfferUserJobCommand extends Command {
    public CorpOfferUserJobCommand() {
        super("cmd_corp_offerjob");
    }
    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params == null) {
            return true;
        }

        String targetedUsername = params[1];

        if (targetedUsername == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return true;
        }

        Habbo targetedHabbo = gameClient.getHabbo().getRoomUnit().getRoom().getRoomUnitManager().getRoomHabboByUsername(targetedUsername);

        if (targetedHabbo == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return true;
        }

        if (!gameClient.getHabbo().getPlayer().getCorpRole().isCanHire()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_hire_not_allowed"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorp().getId() == targetedHabbo.getPlayer().getCorp().getId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_hire_user_has_same_employer"));
            return true;
        }

        CorpInviteEntity corpInvite = new CorpInviteEntity();
        corpInvite.setCorpId(gameClient.getHabbo().getPlayer().getCorp().getId());

        CorpRoleModel corpRole = CorpRoleService.getInstance().getByCorpAndOrderId(gameClient.getHabbo().getPlayer().getCorp().getId(), 1);
        corpInvite.setCorpRoleId(corpRole.getId());

        CorpInviteService.getInstance().create(corpInvite);

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_corp_invite_sent").replace(":username", targetedHabbo.getHabboInfo().getUsername()));

        targetedHabbo.whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_corp_invite_received").replace(":corp",gameClient.getHabbo().getPlayer().getCorp().getDisplayName()));

        return true;
    }
}
