package com.us.roleplay.messages.incoming.combat;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.messages.outgoing.combat.MyWeaponListComposer;

public class ListMyWeaponsEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new MyWeaponListComposer(this.client.getHabbo()));
    }
}