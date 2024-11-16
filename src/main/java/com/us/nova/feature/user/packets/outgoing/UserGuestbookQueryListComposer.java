package com.us.nova.feature.user.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.nova.user.model.UserGuestbookModel;
import com.us.nova.user.service.UserGuestbookService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserGuestbookQueryListComposer extends MessageComposer {
    private final int userId;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.userGuestbookQueryListComposer);

        List<UserGuestbookModel> guestbookEntries = UserGuestbookService.getInstance().getByUserId(this.userId);

        this.response.appendInt(guestbookEntries.size());

        for (UserGuestbookModel guestbookEntry : guestbookEntries) {
            HabboInfo habbo = Emulator.getGameEnvironment().getHabboManager().getOfflineHabboInfo(guestbookEntry.getPostedByUsersId());
            this.response.appendString(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s", guestbookEntry.getId(), habbo.getId(), habbo.getUsername(), habbo.getLook(), guestbookEntry.getMessage(), 0, 0, guestbookEntry.getCreatedAt(), guestbookEntry.getUpdatedAt()));
        }


        return this.response;
    }
}
