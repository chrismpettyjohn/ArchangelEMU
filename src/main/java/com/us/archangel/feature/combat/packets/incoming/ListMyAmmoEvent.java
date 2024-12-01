package com.us.archangel.feature.combat.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.combat.packets.outgoing.MyAmmoListComposer;

public class ListMyAmmoEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new MyAmmoListComposer(this.client.getHabbo()));
    }
}