package com.us.nova.feature.user.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserProfileBannerComposer extends MessageComposer {
    private final Habbo habbo;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.userProfileBannerComposer);
        this.response.appendString(this.habbo.getHabboInfo().getBannerUrl());
        this.response.appendInt(this.habbo.getHabboInfo().getId());
        return this.response;
    }
}
