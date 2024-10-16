package com.us.archangel.feature.police.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.feature.police.packets.outgoing.WantedListComposer;
import com.us.roleplay.police.Bounty;
import com.us.roleplay.police.WantedListManager;
import com.us.roleplay.users.HabboRoleplayHelper;

import java.util.List;

public class WantedListAddUserCommand extends Command {
    public WantedListAddUserCommand() {
        super("cmd_police_add_wanted");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params.length != 3) {
            return true;
        }

        String username = params[1];
        Habbo targetedHabbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(username);

        if (targetedHabbo == null) {
            return true;
        }

        String crime = params[2];

        if (crime == null) {
            return true;
        }

        if (gameClient.getHabbo().getHabboRoleplayStats().getCorp() == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.unemployed"));
            return true;
        }

        if (gameClient.getHabbo().getHabboRoleplayStats().getCorp().getIndustry() != CorpIndustry.Police) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.police_only"));
            return true;
        }

        if (!gameClient.getHabbo().getHabboRoleplayStats().isWorking()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.must_be_working"));
            return true;
        }

        Bounty bounty = new Bounty(targetedHabbo, crime);
        WantedListManager.getInstance().addBounty(bounty);

        List<Habbo> policeOnline = HabboRoleplayHelper.getUsersByCorpIndustry(CorpIndustry.Police);
        List<Habbo> policeWorking = HabboRoleplayHelper.getUsersWorking(policeOnline);

        for (Habbo policeOfficer : policeWorking) {
            policeOfficer.getClient().sendResponse(new WantedListComposer());
        }

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("roleplay.police.wanted_list_changed"));

        return true;
    }
}