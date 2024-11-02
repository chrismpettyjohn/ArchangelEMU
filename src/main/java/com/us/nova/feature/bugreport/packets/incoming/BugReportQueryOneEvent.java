package com.us.nova.feature.bugreport.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.bugreport.model.BugReportModel;
import com.us.nova.bugreport.service.BugReportService;
import com.us.nova.feature.bugreport.packets.outgoing.BugReportDataComposer;

public class BugReportQueryOneEvent extends MessageHandler {

    @Override
    public void handle() {
        int bugReportId = this.packet.readInt();
        BugReportModel bugReportModel = BugReportService.getInstance().getById(bugReportId);

        if (bugReportModel == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.bug-report.does-not-exist"));
            return;
        }

        this.client.sendResponse(new BugReportDataComposer(bugReportModel));
    }

}