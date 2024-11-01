package com.us.nova.feature.bugreport.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.bugreport.entity.BugReportEntity;
import com.us.nova.bugreport.mapper.BugReportMapper;
import com.us.nova.bugreport.model.BugReportModel;
import com.us.nova.bugreport.service.BugReportService;
import com.us.nova.feature.bugreport.packets.outgoing.BugReportDataComposer;

public class BugReportCloseEvent extends MessageHandler {
    static final String BUG_REPORT_CLOSE_PERMISSION = "bug-report-close";

    @Override
    public void handle() {
        boolean canCloseBugReport = this.client.getHabbo().getHabboInfo().getPermissionGroup().hasPermissionRight(BUG_REPORT_CLOSE_PERMISSION, false);

        if (!canCloseBugReport) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.bug-report.close.not-allowed"));
            return;
        }

        int bugReportId = this.packet.readInt();

        BugReportModel bugReportModel = BugReportService.getInstance().getById(bugReportId);
        BugReportEntity bugReportEntity = BugReportMapper.toEntity(bugReportModel);
        bugReportEntity.setClosedAt((int) (System.currentTimeMillis() / 1000L));

        BugReportService.getInstance().update(bugReportId, bugReportEntity);

        this.client.sendResponse(new BugReportDataComposer(BugReportMapper.toModel(bugReportEntity)));
    }
}
