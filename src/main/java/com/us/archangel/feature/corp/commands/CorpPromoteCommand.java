package com.us.archangel.feature.corp.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;

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

        HabboInfo targetedHabbo = Emulator.getGameEnvironment().getHabboManager().getOfflineHabboInfo(targetedUsername);
        PlayerModel targetedPlayer = PlayerService.getInstance().getById(targetedHabbo.getId());

        if (targetedHabbo == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found").replace(":username", targetedUsername));
            return true;
        }

        if (targetedHabbo.getId() == gameClient.getHabbo().getHabboInfo().getId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_user_is_self"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorp().getId() !=targetedPlayer.getCorp().getId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_demote_not_allowed"));
            return true;
        }

        if (!gameClient.getHabbo().getPlayer().getCorpRole().isCanPromote()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_not_allowed"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorpRole().getOrderId() <= targetedPlayer.getCorpRole().getOrderId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_not_allowed"));
            return true;
        }

        CorpRoleModel newRole = CorpRoleService.getInstance().getByCorpAndOrderId(targetedPlayer.getCorp().getId(), targetedPlayer.getCorpRole().getOrderId() + 1);

        if (newRole == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_not_allowed_too_high"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorpRole().getOrderId() < newRole.getOrderId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_not_allowed"));
            return true;
        }

        targetedPlayer.setCorpId(targetedPlayer.getCorp().getId());
        targetedPlayer.setCorpRoleId(newRole.getId());
        targetedPlayer.save();

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_promote_success")
                .replace(":username", targetedHabbo.getUsername())
                .replace(":corp", targetedPlayer.getCorp().getDisplayName())
                .replace(":position", targetedPlayer.getCorpRole().getDisplayName()));

        return true;
    }
}