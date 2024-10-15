package com.us.archangel.feature.combat.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.combat.packets.outgoing.MyWeaponListComposer;

public class ListMyWeaponsEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new MyWeaponListComposer(this.client.getHabbo()));
    }
}