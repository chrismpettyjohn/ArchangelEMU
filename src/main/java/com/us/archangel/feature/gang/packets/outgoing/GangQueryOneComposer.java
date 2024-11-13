package com.us.archangel.feature.gang.packets.outgoing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.guilds.Guild;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GangQueryOneComposer extends MessageComposer {
    private final int gangID;

    @Override
    protected ServerMessage composeInternal() {
        List<PlayerModel> players = PlayerService.getInstance().getByGangId(this.gangID);
        Guild matchingGang = Emulator.getGameEnvironment().getGuildManager().getGuild(this.gangID);
        this.response.init(Outgoing.gangQueryOneComposer);
        this.response.appendInt(matchingGang.getId());;
        this.response.appendInt(matchingGang.getOwnerId());;
        this.response.appendInt(matchingGang.getRoomId());
        this.response.appendString(matchingGang.getName());
        this.response.appendString(matchingGang.getDescription());
        this.response.appendString(matchingGang.getBadge());
        this.response.appendInt(players.size());
        return this.response;
    }

}
