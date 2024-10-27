package com.us.archangel.feature.combat.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.archangel.player.model.PlayerWeaponModel;

public class WeaponReloadEvent extends MessageHandler {
    @Override
    public void handle() {
        PlayerWeaponModel weapon = this.client.getHabbo().getInventory().getWeaponsComponent().getEquippedWeapon();

        if (weapon == null) {
            return;
        }

        if (weapon.getAmmoRemaining() >= weapon.getWeapon().getAmmoCapacity()) {
            return;
        }

        weapon.setAmmoRemaining(0);
        this.client.sendResponse(new UserRoleplayStatsChangeComposer(this.client.getHabbo()));

        Emulator.getThreading().run(() -> {
            this.client.getHabbo().shout(weapon.getWeapon().getReloadMessage());
            weapon.setAmmoRemaining(weapon.getWeapon().getAmmoCapacity());
            this.client.sendResponse(new UserRoleplayStatsChangeComposer(this.client.getHabbo()));
        }, (int) weapon.getWeapon().getReloadTime());


    }
}