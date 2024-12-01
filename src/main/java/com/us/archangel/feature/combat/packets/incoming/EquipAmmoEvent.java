package com.us.archangel.feature.combat.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.combat.commands.EquipCommand;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.archangel.player.model.PlayerAmmoModel;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.service.PlayerAmmoService;
import com.us.archangel.weapon.model.WeaponModel;

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

        this.client.sendResponse(new UserRoleplayStatsChangeComposer(this.client.getHabbo()));
    }
}