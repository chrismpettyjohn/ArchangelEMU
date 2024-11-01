package com.us.nova.feature.bugreport.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.nova.bugreport.model.BugReportModel;
import com.us.nova.bugreport.service.BugReportService;

import java.util.List;

public class BugReportListComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        List<BugReportModel> bugReports = BugReportService.getInstance().getAll();

        this.response.init(Outgoing.bugReportListComposer);

        this.response.appendInt(bugReports.size());

        for (BugReportModel bugReport : bugReports) {
            this.response.appendString(
                        bugReport.getId() + ";" +
                            bugReport.getDisplayName() + ";" +
                            bugReport.getContent() + ";" +
                            bugReport.getCreatedByUserId() + ";" +
                            bugReport.getCreatedAt() + ";" +
                            bugReport.getClosedByUserId() + ";" +
                            bugReport.getClosedAt()
            );
        }

        return this.response;
    }
}
