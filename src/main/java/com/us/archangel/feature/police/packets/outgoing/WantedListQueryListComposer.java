package com.us.archangel.feature.police.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.bounty.model.BountyModel;
import com.us.archangel.bounty.service.BountyService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WantedListQueryListComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        List<BountyModel> bounties = BountyService.getInstance().getAll();
        this.response.init(Outgoing.wantedListComposer);
        this.response.appendInt(bounties.size());
        for (BountyModel bounty : bounties) {
            this.response.appendString(
                    bounty.getHabbo().getHabboInfo().getId()
                            + ";" + bounty.getHabbo().getHabboInfo().getUsername()
                            + ";" + bounty.getHabbo().getHabboInfo().getLook()
                            + ";" + bounty.getCrime().getDisplayName()
            );
        }
        return this.response;
    }
}
