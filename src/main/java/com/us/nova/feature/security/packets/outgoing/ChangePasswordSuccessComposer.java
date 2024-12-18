package com.us.nova.feature.security.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChangePasswordSuccessComposer extends MessageComposer {

    private final Boolean success;
    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.changePasswordSuccessComposer);
        this.response.appendBoolean(this.success);
        return this.response;
    }
}
