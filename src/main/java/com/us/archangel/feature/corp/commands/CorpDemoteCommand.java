package com.us.archangel.feature.corp.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;

public class CorpDemoteCommand extends Command {
    public CorpDemoteCommand() {
        super("cmd_corp_demote");
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
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_demote_user_is_self"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorp().getId() != targetedHabbo.getPlayer().getCorp().getId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_demote_not_allowed"));
            return true;
        }

        if (!gameClient.getHabbo().getPlayer().getCorpRole().isCanDemote()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_demote_not_allowed"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorpRole().getOrderId() <= targetedHabbo.getPlayer().getCorpRole().getOrderId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_demote_not_allowed"));
            return true;
        }

        if (targetedHabbo.getPlayer().getCorpRole().getOrderId() == 1) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_demote_not_allowed_too_low"));
            return true;
        }

        CorpRoleModel newPosition = CorpRoleService.getInstance().getByCorpAndOrderId(targetedHabbo.getPlayer().getCorp().getId(), targetedHabbo.getPlayer().getCorpRole().getOrderId() - 1);

        if (newPosition == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_demote_not_allowed_too_low"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorpRole().getOrderId() < newPosition.getOrderId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_demote_not_allowed"));
            return true;
        }

        gameClient.getHabbo().getPlayer().setCorpId(newPosition.getCorpId());
        gameClient.getHabbo().getPlayer().setCorpRoleId(newPosition.getId());

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_demote_success")
                .replace(":username", targetedHabbo.getHabboInfo().getUsername())
                .replace(":corp", targetedHabbo.getPlayer().getCorp().getDisplayName())
                .replace(":position", targetedHabbo.getPlayer().getCorpRole().getDisplayName()));

        targetedHabbo.shout(Emulator.getTexts().getValue("generic.roleplay.started_new_job").
                replace(":corp", targetedHabbo.getPlayer().getCorp().getDisplayName())
                .replace(":position", newPosition.getDisplayName()));

        return true;
    }
}