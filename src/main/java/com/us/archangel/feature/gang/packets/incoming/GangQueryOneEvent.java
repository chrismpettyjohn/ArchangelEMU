package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangQueryOneComposer;

public class GangQueryOneEvent extends MessageHandler {
    @Override
    public void handle() {
        if (this.client.getHabbo().getPlayer().getGangId() != null) {
            return;
        }
        this.client.sendResponse(new GangQueryOneComposer(this.packet.readInt()));
    }
}
