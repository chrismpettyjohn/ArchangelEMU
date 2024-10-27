package com.us.archangel.feature.police.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.player.enums.PlayerAction;
import com.us.roleplay.RoleplayHelper;

public class CuffCommand extends Command {
    public CuffCommand() {
        super("cmd_police_cuff");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        Habbo targetedHabbo = RoleplayHelper.getInstance().getTarget(gameClient, params);

        if (targetedHabbo == null) {
            return true;
        }

        CorpModel corp = gameClient.getHabbo().getPlayer().getCorp();

        if (corp == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.unemployed"));
            return true;
        }

        if (corp.getIndustry() != CorpIndustry.Police) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.police_only"));
            return true;
        }

        if (!gameClient.getHabbo().getPlayer().isWorking()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.must_be_working"));
            return true;
        }

        if (targetedHabbo.getPlayer().getCurrentAction() == PlayerAction.Cuffed) {
            targetedHabbo.getPlayer().setCurrentAction(PlayerAction.None);
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay_cmd_uncuff_success").replace(":username", targetedHabbo.getHabboInfo().getUsername()));
            return true;
        }

        if (targetedHabbo.getPlayer().getCurrentAction() != PlayerAction.Stunned) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_cuff_must_be_stunned").replace(":username", targetedHabbo.getHabboInfo().getUsername()));
            return true;
        }

        int distanceX = targetedHabbo.getRoomUnit().getCurrentPosition().getX() - gameClient.getHabbo().getRoomUnit().getCurrentPosition().getX();
        int distanceY = targetedHabbo.getRoomUnit().getCurrentPosition().getY() - gameClient.getHabbo().getRoomUnit().getCurrentPosition().getY();

        int rangeInTiles = 1;

        boolean isTargetWithinRange = Math.abs(distanceX) <= rangeInTiles && Math.abs(distanceY) <= rangeInTiles;

        if (!isTargetWithinRange) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.generic_target_too_far").replace(":username", targetedHabbo.getHabboInfo().getUsername()));
            return true;
        }

        targetedHabbo.getPlayer().setCurrentAction(PlayerAction.Cuffed);

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay_cmd_cuff_success").replace(":username", targetedHabbo.getHabboInfo().getUsername()));

        return true;
    }
}