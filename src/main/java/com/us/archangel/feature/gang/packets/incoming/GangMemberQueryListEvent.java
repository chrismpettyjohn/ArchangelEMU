package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangMemberQueryListComposer;

public class GangMemberQueryListEvent  extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new GangMemberQueryListComposer(this.packet.readInt()));
    }
}