package com.us.archangel.feature.police.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.player.enums.PlayerAction;
import com.us.roleplay.RoleplayHelper;

public class EscortCommand extends Command {
    public EscortCommand() {
        super("cmd_police_escort");
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

        if (targetedHabbo.getPlayer().getCurrentAction() != PlayerAction.Cuffed) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_escort_must_be_cuffed").replace(":username", targetedHabbo.getHabboInfo().getUsername()));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getCurrentAction() != PlayerAction.Escorting && gameClient.getHabbo().getPlayer().getEscortingPlayerId() == targetedHabbo.getHabboInfo().getId()) {
            gameClient.getHabbo().getPlayer().setCurrentAction(PlayerAction.None);
            gameClient.getHabbo().getPlayer().setEscortingPlayerId(null);
            return true;
        }

        gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_escort_in_progress").replace(":username", targetedHabbo.getHabboInfo().getUsername()));
        return true;

    }
}