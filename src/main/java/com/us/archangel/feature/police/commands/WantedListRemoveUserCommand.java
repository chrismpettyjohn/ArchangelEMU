package com.us.archangel.feature.police.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.roleplay.corp.CorpTag;
import com.us.roleplay.messages.outgoing.police.WantedListComposer;
import com.us.roleplay.police.Bounty;
import com.us.roleplay.police.WantedListManager;
import com.us.roleplay.users.HabboRoleplayHelper;

import java.util.List;

public class WantedListRemoveUserCommand extends Command {
    public WantedListRemoveUserCommand() {
        super("cmd_police_remove_wanted");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params.length != 2) {
            return true;
        }

        String username = params[1];
        Habbo targetedHabbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(username);

        if (targetedHabbo == null) {
            return true;
        }

        if (gameClient.getHabbo().getHabboRoleplayStats().getCorp() == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.unemployed"));
            return true;
        }

        if (!gameClient.getHabbo().getHabboRoleplayStats().getCorp().getTags().contains(CorpTag.POLICE)) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.police_only"));
            return true;
        }

        if (!gameClient.getHabbo().getHabboRoleplayStats().isWorking()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.must_be_working"));
            return true;
        }

        Bounty bounty = WantedListManager.getInstance().getBountyByUser(targetedHabbo.getHabboInfo().getId());

        if (bounty == null) {
            return true;
        }

        WantedListManager.getInstance().removeBounty(bounty);

        List<Habbo> policeOnline = HabboRoleplayHelper.getUsersByCorpTag(CorpTag.POLICE);
        List<Habbo> policeWorking = HabboRoleplayHelper.getUsersWorking(policeOnline);

        for (Habbo policeOfficer : policeWorking) {
            policeOfficer.getClient().sendResponse(new WantedListComposer());
        }

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("roleplay.police.wanted_list_changed"));

        return true;
    }
}