package com.us.archangel.feature.police.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.feature.police.packets.outgoing.PoliceCallInfoComposer;
import com.us.roleplay.police.PoliceReport;
import com.us.roleplay.police.PoliceReportManager;
import com.us.roleplay.users.HabboRoleplayHelper;

import java.util.List;

public class CallPoliceCommand extends Command {
    public CallPoliceCommand() {
        super("cmd_police_call_for_help");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params == null || params.length == 0) {
            return true;
        }

        String message = params[1];

        if (message == null) {
            return true;
        }

        if (gameClient.getHabbo().getHabboRoleplayStats().isDead()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.generic.not_allowed"));
            return true;
        }

        if (gameClient.getHabbo().getHabboRoleplayStats().isJailed()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.generic.not_allowed"));
            return true;
        }

        PoliceReport policeReport = new PoliceReport(gameClient.getHabbo(), gameClient.getHabbo().getRoomUnit().getRoom(), message, null, false, false);
        PoliceReportManager.getInstance().addPoliceReport(policeReport);

        List<Habbo> policeOnline = HabboRoleplayHelper.getUsersByCorpIndustry(CorpIndustry.Police);
        List<Habbo> policeWorking = HabboRoleplayHelper.getUsersWorking(policeOnline);

        for (Habbo policeOfficer : policeWorking) {
            policeOfficer.getClient().sendResponse(new PoliceCallInfoComposer(policeReport));
        }

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("roleplay.police.cfh_success"));

        return true;
    }
}