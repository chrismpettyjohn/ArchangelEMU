package com.us.archangel.feature.police.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.roleplay.RoleplayHelper;
import com.us.archangel.feature.police.actions.ServeJailTimeAction;
import com.us.archangel.feature.police.packets.outgoing.UserArrestedComposer;
import com.us.roleplay.police.Bounty;
import com.us.roleplay.police.WantedListManager;

import java.util.Collection;

public class ArrestCommand extends Command {
    public ArrestCommand() {
        super("cmd_police_arrest");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        Habbo targetedHabbo = RoleplayHelper.getInstance().getTarget(gameClient, params);

        if (targetedHabbo == null) {
            return true;
        }

        String crime = params[2];
        int prisonTime = Integer.parseInt(params[3]);

        if (crime == null || prisonTime <= 0) {
            return true;
        }

        CorpModel corp = gameClient.getHabbo().getHabboRoleplayStats().getCorp();

        if (corp == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.unemployed"));
            return true;
        }

        if (corp.getIndustry() != CorpIndustry.Police) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.police_only"));
            return true;
        }

        if (!gameClient.getHabbo().getHabboRoleplayStats().isWorking()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.must_be_working"));
            return true;
        }

        if (!targetedHabbo.getHabboRoleplayStats().isCuffed()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_arrest_must_be_cuffed").replace(":username", targetedHabbo.getHabboInfo().getUsername()));
            return true;
        }

        if (gameClient.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId() != corp.getRoomId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_arrest_must_be_in_station"));
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

        gameClient.getHabbo().getHabboRoleplayStats().setIsEscorting(null);
        targetedHabbo.getHabboRoleplayStats().setIsCuffed(false);
        targetedHabbo.getHabboRoleplayStats().setIsStunned(false);

        Bounty bounty = WantedListManager.getInstance().getBountyByUser(targetedHabbo.getHabboInfo().getId());

        if (bounty != null) {
            WantedListManager.getInstance().removeBounty(bounty);
        }

        Collection<Habbo> onlineHabbos = Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values();

        for (Habbo onlineHabbo : onlineHabbos) {
            onlineHabbo.getClient().sendResponse(new UserArrestedComposer(targetedHabbo, gameClient.getHabbo()));
        }

        Emulator.getThreading().run(new ServeJailTimeAction(targetedHabbo, crime, prisonTime));

        return true;
    }
}