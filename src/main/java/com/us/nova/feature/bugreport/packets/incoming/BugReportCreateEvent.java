package com.us.nova.feature.bugreport.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.bugreport.entity.BugReportEntity;
import com.us.nova.bugreport.mapper.BugReportMapper;
import com.us.nova.bugreport.service.BugReportService;
import com.us.nova.feature.bugreport.packets.outgoing.BugReportDataComposer;

public class BugReportCreateEvent extends MessageHandler {
    @Override
    public void handle() {
        String displayName = this.packet.readString();
        String content = this.packet.readString();

        BugReportEntity bugReportEntity = new BugReportEntity();
        bugReportEntity.setDisplayName(displayName);
        bugReportEntity.setContent(content);
        bugReportEntity.setCreatedByUserId(this.client.getHabbo().getHabboInfo().getId());
        bugReportEntity.setCreatedAt((int) (System.currentTimeMillis() / 1000L));

        BugReportService.getInstance().create(BugReportMapper.toModel(bugReportEntity));

        this.client.sendResponse(new BugReportDataComposer(BugReportMapper.toModel(bugReportEntity)));
    }
}
