package com.us.archangel.feature.weapon.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.ammo.enums.AmmoSize;
import com.us.archangel.ammo.enums.AmmoType;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.service.AmmoService;
import com.us.archangel.feature.weapon.packets.outgoing.PlayerWeaponListComposer;
import com.us.archangel.player.entity.PlayerWeaponEntity;
import com.us.archangel.player.mapper.PlayerWeaponMapper;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.model.PlayerWeaponPermissions;
import com.us.archangel.player.service.PlayerWeaponService;
import com.us.archangel.weapon.enums.WeaponType;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;

import java.util.List;

public class PlayerWeaponCreateEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canCreatePlayerWeapons = this.client.getHabbo().hasPermissionRight(PlayerWeaponPermissions.CREATE);

        if (!canCreatePlayerWeapons) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        int weaponId = this.packet.readInt();
        int userId = this.packet.readInt();

        WeaponModel weapon = WeaponService.getInstance().getById(weaponId);

        if (weapon == null) {
            return;
        }

        if (weapon.getAmmoSize() != AmmoSize.NONE) {
            AmmoModel weaponAmmoModel = AmmoService.getInstance().getBySizeAndType(weapon.getAmmoSize(), AmmoType.STANDARD);

            if (weaponAmmoModel == null) {
                this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.store_offer.transaction_failed"));
                throw new RuntimeException("weapon has no matching ammo");
            }

            PlayerWeaponService.getInstance().createWithAmmo(
                    weapon,
                    this.client.getHabbo().getHabboInfo().getId(),
                    weaponAmmoModel
            );
        }

        if (weapon.getAmmoSize() == AmmoSize.NONE) {
            PlayerWeaponEntity playerWeaponEntity = new PlayerWeaponEntity();
            playerWeaponEntity.setUserId(userId);
            playerWeaponEntity.setWeaponId(weapon.getId());
            PlayerWeaponService.getInstance().create(PlayerWeaponMapper.toModel(playerWeaponEntity));
        }

        List<PlayerWeaponModel> playerWeaponModels = PlayerWeaponService.getInstance().getByUserID(userId);
        this.client.sendResponse(new PlayerWeaponListComposer(playerWeaponModels));
    }
}
