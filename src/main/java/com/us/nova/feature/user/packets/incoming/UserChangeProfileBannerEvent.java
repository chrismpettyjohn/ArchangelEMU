package com.us.nova.feature.user.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.user.packets.outgoing.UserProfileBannerComposer;

public class UserChangeProfileBannerEvent extends MessageHandler {
    @Override
    public void handle() {
        String imageUrl = this.packet.readString();

        if (imageUrl == null || imageUrl.isEmpty() || !imageUrl.contains("https://i.imgur.com/")) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.change_banner.invalid"));
            return;
        }

        this.client.getHabbo().getHabboInfo().setBannerUrl(imageUrl);
        this.client.getHabbo().getHabboInfo().run();

        this.client.sendResponse(new UserProfileBannerComposer(this.client.getHabbo()));
    }
}