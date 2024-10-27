package com.us.archangel.feature.corp.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;

public class CorpPromoteCommand extends Command {
    public CorpPromoteCommand() {
        super("cmd_corp_promote");
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
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_user_is_self"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorp().getId() != targetedHabbo.getPlayer().getCorp().getId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_demote_not_allowed"));
            return true;
        }

        if (!gameClient.getHabbo().getPlayer().getCorpRole().isCanPromote()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_not_allowed"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorpRole().getOrderId() <= targetedHabbo.getPlayer().getCorpRole().getOrderId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_not_allowed"));
            return true;
        }

        CorpRoleModel newRole = CorpRoleService.getInstance().getByCorpAndOrderId(targetedHabbo.getPlayer().getCorp().getId(), targetedHabbo.getPlayer().getCorpRole().getOrderId() + 1);

        if (newRole == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_not_allowed_too_high"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorpRole().getOrderId() < newRole.getOrderId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_not_allowed"));
            return true;
        }

        targetedHabbo.getPlayer().setCorpId(targetedHabbo.getPlayer().getCorp().getId());
        targetedHabbo.getPlayer().setCorpRoleId(newRole.getId());

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_success")
                .replace(":username", targetedHabbo.getHabboInfo().getUsername())
                .replace(":corp", targetedHabbo.getPlayer().getCorp().getDisplayName())
                .replace(":position", targetedHabbo.getPlayer().getCorpRole().getDisplayName()));

        targetedHabbo.shout(Emulator.getTexts().getValue("generic.roleplay.started_new_job").
                replace(":corp", targetedHabbo.getPlayer().getCorp().getDisplayName())
                .replace(":position", newRole.getDisplayName()));

        return true;
    }
}