package com.us.archangel.feature.police.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.roleplay.police.PoliceReport;
import com.us.roleplay.police.PoliceReportManager;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PoliceListReportsComposer extends MessageComposer {
    public final Habbo habbo;
    @Override
    protected ServerMessage composeInternal() {
        List<PoliceReport> policeReports = PoliceReportManager.getInstance().getPoliceReportsByRespondingOfficer(this.habbo);
        this.response.init(Outgoing.policeListReportsComposer);
        this.response.appendInt(policeReports.size());
        for (PoliceReport policeReport : policeReports) {
            this.response.appendString(
                    policeReport.getReportingUser().getHabboInfo().getId()
                            + ";" + policeReport.getReportingUser().getHabboInfo().getUsername()
                            + ";" + policeReport.getReportingUser().getHabboInfo().getLook()
                            + ";" + policeReport.getReportingUser().getRoomUnit().getRoom().getRoomInfo().getId()
                            + ";" + policeReport.getReportingUser().getRoomUnit().getRoom().getRoomInfo().getName()
                            + ";" + Optional.of(policeReport.getRespondingOfficer().getHabboInfo().getId()).orElse(-1)
                            + ";" + Optional.ofNullable(policeReport.getRespondingOfficer().getHabboInfo().getUsername()).orElse("")
                            + ";" + Optional.ofNullable(policeReport.getRespondingOfficer().getHabboInfo().getLook()).orElse("-")
            );
        }
        return this.response;
    }
}
