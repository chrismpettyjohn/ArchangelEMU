package com.us.archangel.feature.gang.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.service.GangService;

import java.util.List;

public class GangQueryListComposer extends MessageComposer {

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.gangQueryListComposer);

        List<GangModel> gangs = GangService.getInstance().getAll();

        this.response.appendInt(gangs.size());

        for (GangModel gang : gangs) {
            this.response.appendString(String.format("%s;%s;%s;%s;%s", String.valueOf(gang.getId()), gang.getDisplayName(), gang.getDescription(), gang.getBadge(), String.valueOf(gang.getUserId())));
        }

        return this.response;
    }

}
