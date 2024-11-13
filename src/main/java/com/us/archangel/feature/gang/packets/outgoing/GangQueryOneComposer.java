package com.us.archangel.feature.gang.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.guilds.Guild;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GangQueryOneComposer extends MessageComposer {
    private final int gangID;

    @Override
    protected ServerMessage composeInternal() {
        Guild matchingGang = Emulator.getGameEnvironment().getGuildManager().getGuild(this.gangID);
        this.response.init(Outgoing.gangQueryOneComposer);
        this.response.appendInt(matchingGang.getId());;
        this.response.appendInt(matchingGang.getOwnerId());;
        this.response.appendInt(matchingGang.getRoomId());
        this.response.appendString(matchingGang.getName());
        this.response.appendString(matchingGang.getDescription());
        this.response.appendString(matchingGang.getBadge());
        return this.response;
    }

}
