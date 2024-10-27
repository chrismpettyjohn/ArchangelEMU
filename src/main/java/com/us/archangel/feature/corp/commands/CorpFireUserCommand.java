package com.us.archangel.feature.corp.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;

import java.util.List;

public class CorpFireUserCommand extends Command {
    public CorpFireUserCommand() {
        super("cmd_corp_fire");
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
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found").replace(":username", targetedUsername));
            return true;
        }

        if (targetedHabbo == gameClient.getHabbo()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_fire_user_is_self"));
            return true;
        }

        if (!gameClient.getHabbo().getPlayer().getCorpRole().isCanFire()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_fire_not_allowed"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorpRole().getOrderId() <= targetedHabbo.getPlayer().getCorpRole().getOrderId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_fire_not_allowed"));
            return true;
        }

        List<CorpModel> welfareCorps = CorpService.getInstance().findManyByIndustry(CorpIndustry.PublicAid);

        if (welfareCorps.isEmpty()) {
            throw new RuntimeException("no welfare corp found");
        }

        CorpModel welfareCorp = welfareCorps.get(0);

        CorpRoleModel welfareRole = CorpRoleService.getInstance().getByCorpAndOrderId(welfareCorp.getId(), 1);

        gameClient.getHabbo().getPlayer().setCorpId(welfareCorp.getId());
        gameClient.getHabbo().getPlayer().setCorpRoleId(welfareRole.getId());

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_fire_success").replace(":username", targetedHabbo.getHabboInfo().getUsername()));
        targetedHabbo.shout(Emulator.getTexts().getValue("commands.roleplay.cmd_fire_impacted"));

        return true;
    }
}