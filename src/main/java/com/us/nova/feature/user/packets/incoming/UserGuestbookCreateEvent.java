package com.us.nova.feature.user.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.user.packets.outgoing.UserGuestbookQueryListComposer;
import com.us.nova.feature.user.packets.outgoing.UserGuestbookQueryOneComposer;
import com.us.nova.user.entity.UserGuestbookEntity;
import com.us.nova.user.mapper.UserGuestbookMapper;
import com.us.nova.user.model.UserGuestbookModel;
import com.us.nova.user.service.UserGuestbookService;

public class UserGuestbookCreateEvent extends MessageHandler {
    @Override
    public void handle() {
        HabboInfo recipientHabbo = Emulator.getGameEnvironment().getHabboManager().getOfflineHabboInfo(this.packet.readInt());

        if (recipientHabbo == null) {
            return;
        }

        UserGuestbookEntity guestbookEntry = new UserGuestbookEntity();
        guestbookEntry.setPostedOnUsersId(recipientHabbo.getId());
        guestbookEntry.setPostedByUsersId(this.client.getHabbo().getHabboInfo().getId());
        guestbookEntry.setMessage(this.packet.readString());
        guestbookEntry.setCreatedAt((int) (System.currentTimeMillis() / 1000));
        guestbookEntry.setUpdatedAt((int) (System.currentTimeMillis() / 1000));

        UserGuestbookModel savedGuestbookEntry = UserGuestbookService.getInstance().create(UserGuestbookMapper.toModel(guestbookEntry));

        this.client.sendResponse(new UserGuestbookQueryListComposer(recipientHabbo.getId()));
        this.client.sendResponse(new UserGuestbookQueryOneComposer(savedGuestbookEntry.getId()));

    }
}
