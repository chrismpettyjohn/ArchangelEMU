package com.us.nova.feature.user.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.nova.user.model.UserGuestbookModel;
import com.us.nova.user.service.UserGuestbookService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserGuestbookQueryOneComposer extends MessageComposer {
    private final int guestbookEntryId;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.userGuestbookQueryOneComposer);

        UserGuestbookModel guestbookEntry = UserGuestbookService.getInstance().getById(this.guestbookEntryId);
        HabboInfo habbo = Emulator.getGameEnvironment().getHabboManager().getOfflineHabboInfo(guestbookEntry.getPostedByUsersId());
        this.response.appendInt(guestbookEntryId);
        this.response.appendString(habbo.getUsername());
        this.response.appendString(habbo.getLook());
        this.response.appendString(guestbookEntry.getMessage());
        this.response.appendInt(0); // TODO: upvotes
        this.response.appendInt(0); // TODO: downvotes
        this.response.appendInt(guestbookEntry.getCreatedAt());
        this.response.appendInt(guestbookEntry.getUpdatedAt());

        return this.response;
    }
}
