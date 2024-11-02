package com.us.nova.feature.bugreport.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.bugreport.model.BugReportPermissions;
import com.us.nova.bugreport.service.BugReportService;

public class BugReportDeleteEvent extends MessageHandler {

    @Override
    public void handle() {
        boolean canDeleteBugReport = this.client.getHabbo().getHabboInfo().getPermissionGroup().hasPermissionRight(BugReportPermissions.DELETE, false);

        if (!canDeleteBugReport) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.bug-report.delete.not-allowed"));
            return;
        }

        BugReportService.getInstance().deleteById(this.client.getHabbo().getHabboInfo().getId());
    }
}
