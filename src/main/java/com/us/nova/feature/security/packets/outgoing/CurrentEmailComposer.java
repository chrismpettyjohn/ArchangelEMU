package com.us.nova.feature.security.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CurrentEmailComposer extends MessageComposer {

    private final Habbo habbo;
    private final boolean success;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.currentEmailComposer);
        this.response.appendString(this.habbo.getHabboInfo().getMail());
        this.response.appendBoolean(this.success);
        return this.response;
    }
}
