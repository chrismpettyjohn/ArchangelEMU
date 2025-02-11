package com.us.nova.feature.bugreport.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.nova.bugreport.model.BugReportModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BugReportListComposer extends MessageComposer {
    private final List<BugReportModel> bugReports;
    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.bugReportListComposer);

        this.response.appendInt(this.bugReports.size());

        for (BugReportModel bugReport : this.bugReports) {
            Habbo createdByHabbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(bugReport.getCreatedByUserId());
            Habbo closedByHabbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(bugReport.getClosedByUserId());
            this.response.appendString(
                        bugReport.getId() + ";" +
                            bugReport.getDisplayName() + ";" +
                            bugReport.getContent() + ";" +
                            bugReport.getCreatedByUserId() + ";" +
                            createdByHabbo.getHabboInfo().getUsername() + ";" +
                            bugReport.getCreatedAt() + ";" +
                            (bugReport.getClosedByUserId() != null ? bugReport.getClosedByUserId() : -1) + ";" +
                            (closedByHabbo != null ? closedByHabbo.getHabboInfo().getUsername() : "") + ";" +
                            (bugReport.getClosedAt() != null ? bugReport.getClosedAt() : -1)

            );
        }

        return this.response;
    }
}
