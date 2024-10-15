package com.us.roleplay.messages.outgoing.police;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.roleplay.police.Bounty;
import com.us.roleplay.police.WantedListManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WantedListComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        List<Bounty> bounties = WantedListManager.getInstance().getBounties();
        this.response.init(Outgoing.wantedListComposer);
        this.response.appendInt(bounties.size());
        for (Bounty bounty : bounties) {
            this.response.appendString(
                    bounty.getHabbo().getHabboInfo().getId()
                            + ";" + bounty.getHabbo().getHabboInfo().getUsername()
                            + ";" + bounty.getHabbo().getHabboInfo().getLook()
                            + ";" + bounty.getCrime()
            );
        }
        return this.response;
    }
}
