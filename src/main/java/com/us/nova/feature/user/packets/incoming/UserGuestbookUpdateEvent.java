package com.us.nova.feature.user.packets.incoming;

import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.nova.feature.user.packets.outgoing.UserGuestbookQueryListComposer;
import com.us.nova.user.model.UserGuestbookModel;
import com.us.nova.user.service.UserGuestbookService;

public class UserGuestbookUpdateEvent extends MessageHandler {
    @Override
    public void handle() {
        UserGuestbookModel guestbookEntry = UserGuestbookService.getInstance().getById(this.packet.readInt());

        if (guestbookEntry == null) {
            return;
        }

        if (guestbookEntry.getPostedByUsersId() != this.client.getHabbo().getHabboInfo().getId() && !this.client.getHabbo().hasPermissionRight(Permission.ACC_MANAGE_GUESTBOOK)) {
            return;
        }

        guestbookEntry.setMessage(this.packet.readString());
        guestbookEntry.setUpdatedAt((int) System.currentTimeMillis());
        guestbookEntry.update();

        this.client.sendResponse(new UserGuestbookQueryListComposer(guestbookEntry.getPostedOnUsersId()));
    }
}