package com.us.archangel.feature.police.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.roleplay.police.PoliceReport;
import com.us.roleplay.police.PoliceReportManager;

public class ResolveReportCommand extends Command {
    public ResolveReportCommand() {
        super("cmd_police_resolve_report");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params.length != 3) {
            return true;
        }

        int policeReportIndex = Integer.parseInt(params[1]);
        boolean flagged = Boolean.parseBoolean(params[2]);

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

        PoliceReport policeReport = PoliceReportManager.getInstance().getPoliceReportByIndex(policeReportIndex);

        if (policeReport == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.police.cfh_invalid"));
            return true;
        }


        policeReport.setResolved(true);

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("roleplay.police.cfh_resolved"));

        if (flagged) {
            policeReport.setFlagged(flagged);
            policeReport.getReportingUser().whisper(Emulator.getTexts().getValue("roleplay.police.cfh_flagged"));
            return true;
        }

        policeReport.getReportingUser().whisper(Emulator.getTexts()
                .getValue("roleplay.police.cfh_replied")
                .replace(":username", policeReport.getReportingUser().getHabboInfo().getUsername())
        );
        return true;
    }
}