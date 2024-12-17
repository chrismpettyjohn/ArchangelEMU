package com.us.archangel.feature.combat.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.archangel.player.model.PlayerAmmoModel;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.service.PlayerAmmoService;

public class EquipAmmoEvent extends MessageHandler {
    @Override
    public void handle() {
        PlayerWeaponModel playerWeapon = this.client.getHabbo().getInventory().getWeaponsComponent().getEquippedWeapon();
        if (playerWeapon == null) {
            return;
        }

        int ammoId = this.packet.readInt();
        PlayerAmmoModel playerAmmo = PlayerAmmoService.getInstance().getById(ammoId);

        if (playerAmmo == null) {
            return;
        }

        if (playerWeapon.getWeapon().getAmmoSize() != playerAmmo.getAmmo().getSize()) {
            return;
        }

        playerWeapon.setAmmoId(ammoId);

        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("roleplay.equip_ammo.success")
                .replace(":ammo", playerAmmo.getAmmo().getDisplayName())
        );

        this.client.sendResponse(new UserRoleplayStatsChangeComposer(this.client.getHabbo()));
    }
}