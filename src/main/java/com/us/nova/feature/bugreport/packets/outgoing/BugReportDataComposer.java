package com.us.nova.feature.bugreport.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.nova.bugreport.model.BugReportModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BugReportDataComposer extends MessageComposer {
    protected final BugReportModel bugReport;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.bugReportDataComposer);

        this.response.appendInt(this.bugReport.getId());
        this.response.appendString(this.bugReport.getDisplayName());
        this.response.appendString(this.bugReport.getContent());
        this.response.appendInt(this.bugReport.getCreatedByUserId());
        this.response.appendInt(this.bugReport.getCreatedAt());
        this.response.appendInt(this.bugReport.getClosedByUserId());
        this.response.appendInt(this.bugReport.getClosedAt());

        return this.response;
    }
}
