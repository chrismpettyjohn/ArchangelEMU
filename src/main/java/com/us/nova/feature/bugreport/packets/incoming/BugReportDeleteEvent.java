package com.us.nova.feature.bugreport.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.bugreport.service.BugReportService;

public class BugReportDeleteEvent extends MessageHandler {
    static final String BUG_REPORT_DELETE_PERMISSION = "bug-report-delete";

    @Override
    public void handle() {
        boolean canDeleteBugReport = this.client.getHabbo().getHabboInfo().getPermissionGroup().hasPermissionRight(BUG_REPORT_DELETE_PERMISSION, false);

        if (!canDeleteBugReport) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.bug-report.delete.not-allowed"));
            return;
        }

        BugReportService.getInstance().deleteById(this.client.getHabbo().getHabboInfo().getId());
    }
}
