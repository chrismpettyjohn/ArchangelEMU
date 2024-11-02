package com.us.nova.feature.bugreport.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.bugreport.model.BugReportModel;
import com.us.nova.bugreport.model.BugReportPermissions;
import com.us.nova.bugreport.service.BugReportService;
import com.us.nova.feature.bugreport.packets.outgoing.BugReportListComposer;

import java.util.List;

public class BugReportQueryListEvent extends MessageHandler {

    @Override
    public void handle() {
        List<BugReportModel> bugReports = BugReportService.getInstance().getAll();

        if (!this.client.getHabbo().hasPermissionRight(BugReportPermissions.READ)) {
            bugReports = bugReports.stream()
                    .filter(bugReport -> bugReport.getCreatedByUserId() == this.client.getHabbo().getHabboInfo().getId())
                    .toList();
        }

        this.client.sendResponse(new BugReportListComposer(bugReports));
    }

}