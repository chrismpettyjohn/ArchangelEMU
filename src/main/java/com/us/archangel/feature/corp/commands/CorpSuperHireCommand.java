package com.us.archangel.feature.corp.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;

public class CorpSuperHireCommand extends Command {
    public CorpSuperHireCommand() {
        super("cmd_corp_superhire");
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

        Integer corporationId = params[2] != null ? Integer.parseInt(params[2]) : null;

        if (corporationId == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_superhire_invalid_corp"));
            return true;
        }

        CorpModel matchingCorp = CorpService.getInstance().getById(corporationId);

        if (matchingCorp == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_superhire_invalid_corp"));
            return true;
        }


        Integer positionId = params[3] != null ? Integer.parseInt(params[3]) : null;

        if (positionId == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_superhire_invalid_position"));
            return true;
        }

        CorpRoleModel matchingPosition = CorpRoleService.getInstance().getById(positionId);

        if (matchingPosition == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_superhire_invalid_position"));
            return true;
        }

        targetedPlayer.setCorpId(corporationId);
        targetedPlayer.setCorpRoleId(positionId);
        targetedPlayer.save();

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_superhire_success")
                .replace(":username", targetedHabbo.getUsername())
                .replace(":corp", matchingCorp.getDisplayName())
                .replace(":position", matchingPosition.getDisplayName()));

        return true;
    }
}