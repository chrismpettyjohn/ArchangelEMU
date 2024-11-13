package com.us.archangel.feature.gang.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.service.GangService;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;

import java.util.List;

public class GangQueryListComposer extends MessageComposer {

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.gangQueryListComposer);

        List<GangModel> gangs = GangService.getInstance().getAll();

        this.response.appendInt(gangs.size());

        for (GangModel gang : gangs) {
            List<PlayerModel> players =PlayerService.getInstance().getByGangId(gang.getId());
            this.response.appendString(String.format("%s;%s;%s;%s;%s;%s", gang.getId(), gang.getDisplayName(), gang.getDescription(), gang.getBadge(), gang.getUserId(), players.size()));
        }

        return this.response;
    }

}
