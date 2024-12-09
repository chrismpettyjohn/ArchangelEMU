package com.us.archangel.feature.combat.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.archangel.player.model.PlayerWeaponModel;

public class WeaponReloadEvent extends MessageHandler {
    @Override
    public void handle() {
        PlayerWeaponModel weapon = this.client.getHabbo().getInventory().getWeaponsComponent().getEquippedWeapon();

        if (weapon == null || weapon.getAmmoRemaining() >= weapon.getWeapon().getAmmoCapacity()) {
            return;
        }

        if (weapon.getPlayerAmmo() == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.reload.out_of_ammo"));
            return;
        }

        if (weapon.getPlayerAmmo().getAmmoRemaining() <= 0) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.reload.out_of_ammo"));
            return;
        }

        int ammoToReload = Math.min(weapon.getWeapon().getAmmoCapacity() - weapon.getAmmoRemaining(), weapon.getPlayerAmmo().getAmmoRemaining());

        weapon.getPlayerAmmo().depleteAmmo(ammoToReload);
        weapon.setAmmoRemaining(weapon.getAmmoRemaining() + ammoToReload);
        this.client.sendResponse(new UserRoleplayStatsChangeComposer(this.client.getHabbo()));

        Emulator.getThreading().run(() -> {
            this.client.getHabbo().shout(weapon.getWeapon().getReloadMessage());
            weapon.setAmmoRemaining(ammoToReload);
            this.client.sendResponse(new UserRoleplayStatsChangeComposer(this.client.getHabbo()));
        }, weapon.getWeapon().getReloadTime());
    }

}