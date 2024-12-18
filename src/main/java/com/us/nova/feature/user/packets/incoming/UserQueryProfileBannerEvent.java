package com.us.nova.feature.user.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.user.packets.outgoing.UserProfileBannerComposer;

public class UserQueryProfileBannerEvent extends MessageHandler {
    @Override
    public void handle() {
        int userId = this.packet.readInt();
        Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(userId);
        if (habbo == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.change_banner.not_found"));
            return;
        }
        this.client.sendResponse(new UserProfileBannerComposer(habbo));
    }
}