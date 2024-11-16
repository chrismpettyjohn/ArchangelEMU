package com.us.nova.feature.notification.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationComposer extends MessageComposer {
    protected final String message;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.notificationComposer);
        this.response.appendString(this.message);
        return this.response;
    }
}
