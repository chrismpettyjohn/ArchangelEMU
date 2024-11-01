package com.us.nova.feature.bugreport.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.bugreport.entity.BugReportEntity;
import com.us.nova.bugreport.mapper.BugReportMapper;
import com.us.nova.bugreport.model.BugReportModel;
import com.us.nova.bugreport.service.BugReportService;
import com.us.nova.feature.bugreport.packets.outgoing.BugReportDataComposer;

public class BugReportUpdateEvent extends MessageHandler {
    @Override
    public void handle() {
        int bugReportId = this.packet.readInt();

        BugReportModel bugReportModel = BugReportService.getInstance().getById(bugReportId);

        if (bugReportModel.getCreatedByUserId() != this.client.getHabbo().getHabboInfo().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.bug-report.update.not-allowed"));
            return;
        }

        String displayName = this.packet.readString();
        String content = this.packet.readString();

        BugReportEntity bugReportEntity = BugReportMapper.toEntity(bugReportModel);

        bugReportEntity.setDisplayName(displayName);
        bugReportEntity.setContent(content);

        BugReportService.getInstance().update(bugReportId, bugReportEntity);

        this.client.sendResponse(new BugReportDataComposer(BugReportMapper.toModel(bugReportEntity)));
    }
}
