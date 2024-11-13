package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangRoleQueryListComposer;

public class GangRoleQueryListEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new GangRoleQueryListComposer(this.packet.readInt()));
    }
}
