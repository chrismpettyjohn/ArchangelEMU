package com.us.archangel.feature.gang.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.service.GangService;
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
        GangModel matchingGang = GangService.getInstance().getById(this.gangID);
        this.response.init(Outgoing.gangQueryOneComposer);
        this.response.appendInt(matchingGang.getId());
        this.response.appendString(matchingGang.getDisplayName());
        this.response.appendString(matchingGang.getDescription());
        this.response.appendString(matchingGang.getBadge());
        this.response.appendInt(matchingGang.getUserId());
        this.response.appendInt(matchingGang.getRoomId());
        this.response.appendInt(players.size());
        return this.response;
    }

}

