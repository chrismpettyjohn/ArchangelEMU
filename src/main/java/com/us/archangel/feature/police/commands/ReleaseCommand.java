package com.us.archangel.feature.police.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.player.enums.PlayerAction;

public class ReleaseCommand extends Command {
    public ReleaseCommand() {
        super("cmd_police_release");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params == null || params.length == 0) {
            return true;
        }

        String targetedUsername = params[1];

        if (gameClient.getHabbo().getPlayer().getCurrentAction() == PlayerAction.Stunned || gameClient.getHabbo().getPlayer().getCurrentAction() == PlayerAction.Cuffed || gameClient.getHabbo().getPlayer().isDead()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.generic.not_allowed"));
            return true;
        }

        if (targetedUsername == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return true;
        }

        Habbo targetedHabbo = gameClient.getHabbo().getRoomUnit().getRoom().getRoomUnitManager().getRoomHabboByUsername(targetedUsername);

        if (targetedHabbo == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found").replace(":username", targetedUsername));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCorp().getIndustry() != CorpIndustry.Police) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.police_only"));
            return true;
        }

        if (!gameClient.getHabbo().getPlayer().isWorking()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.must_be_working"));
            return true;
        }

        targetedHabbo.getPlayer().setJailTimeRemainingSecs(0);

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay_cmd_release_success").replace(":username", targetedHabbo.getHabboInfo().getUsername()));

        return true;
    }
}