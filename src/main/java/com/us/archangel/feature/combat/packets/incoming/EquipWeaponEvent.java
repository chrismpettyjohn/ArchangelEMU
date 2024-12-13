package com.us.archangel.feature.combat.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.threading.runnables.RoomUnitGiveHanditem;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.archangel.player.model.PlayerAmmoModel;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.service.PlayerAmmoService;
import com.us.archangel.player.service.PlayerWeaponService;
import com.us.archangel.weapon.context.WeaponContext;
import com.us.archangel.weapon.enums.WeaponType;
import com.us.archangel.weapon.model.WeaponModel;

public class EquipWeaponEvent extends MessageHandler {
    @Override
    public void handle() {
        int playerWeaponId = this.packet.readInt();

        if (playerWeaponId == 0) {
            PlayerWeaponModel prevEquippedWeapon = this.client.getHabbo().getInventory().getWeaponsComponent().getEquippedWeapon();
            this.client.getHabbo().getInventory().getWeaponsComponent().setEquippedWeapon(null);
            this.client.getHabbo().getRoomUnit().giveEffect(0, -1);
            if (this.client.getHabbo().getRoomUnit().getHandItem() > 0) {
                Emulator.getThreading().run(new RoomUnitGiveHanditem(this.client.getHabbo().getRoomUnit(), this.client.getHabbo().getRoomUnit().getRoom(), 0));
            }
            this.client.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.client.getHabbo()).compose());
            this.client.getHabbo().shout(prevEquippedWeapon.getWeapon().getUnequipMessage().replace(":displayName", prevEquippedWeapon.getWeapon().getDisplayName()));
            return;
        }

        PlayerWeaponModel playerWeapon = PlayerWeaponService.getInstance().getById(playerWeaponId);

        if (playerWeapon == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_equip_weapon_not_found"));
            return;
        }

        if (playerWeapon.getWeapon().getType() == WeaponType.GUN && playerWeapon.getAmmoId() == 0) {
            PlayerAmmoModel playerAmmo = PlayerAmmoService.getInstance().getByAmmoSize(playerWeapon.getWeapon().getAmmoSize()).get(0);

            if (playerAmmo == null) {
                this.client.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.compatible_ammo_not_found").replace(":ammoSize", playerWeapon.getWeapon().getAmmoSize().toString()));
                return;
            }

            playerWeapon.setAmmoId(playerAmmo.getId());
        }

        this.client.getHabbo().getInventory().getWeaponsComponent().setEquippedWeapon(playerWeapon);
;

        if (playerWeapon.getWeapon().getEquipEffect() > 0) {
            this.client.getHabbo().getRoomUnit().giveEffect(WeaponContext.getInstance().get(playerWeapon.getId()).getEquipEffect(), -1);
        }

        if (playerWeapon.getWeapon().getEquipHandItem() > 0) {
            Emulator.getThreading().run(new RoomUnitGiveHanditem(this.client.getHabbo().getRoomUnit(), this.client.getHabbo().getRoomUnit().getRoom(), playerWeapon.getWeapon().getEquipHandItem()));
        }

        this.client.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(this.client.getHabbo()).compose());
        this.client.getHabbo().shout(playerWeapon.getWeapon().getEquipMessage().replace(":displayName", playerWeapon.getWeapon().getDisplayName()));
        this.client.sendResponse(new UserRoleplayStatsChangeComposer(this.client.getHabbo()).compose());
    }
}