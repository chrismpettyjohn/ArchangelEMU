package com.us.nova.feature.user.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserGuestbookQueryOneComposer extends MessageComposer {
    private final int guestbookEntryId;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.userGuestbookQueryOneComposer);

        return this.response;
    }
}
