package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangRoleQueryOneComposer;

public class GangRoleQueryOneEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new GangRoleQueryOneComposer(this.packet.readInt()));
    }
}
