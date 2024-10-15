package com.us.archangel.feature.player.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.player.packets.outgoing.RoleplayPermissionsComposer;

public class RoleplayPermissionsEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new RoleplayPermissionsComposer(this.client.getHabbo()));
    }
}