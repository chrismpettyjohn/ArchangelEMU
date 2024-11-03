package com.us.archangel.feature.weapon.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.weapon.packets.outgoing.PlayerWeaponListComposer;
import com.us.archangel.player.entity.PlayerWeaponEntity;
import com.us.archangel.player.mapper.PlayerWeaponMapper;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.model.PlayerWeaponPermissions;
import com.us.archangel.player.service.PlayerWeaponService;
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

        WeaponModel weapon = WeaponService.getInstance().getById(this.packet.readInt());

        if (weapon == null) {
            return;
        }

        PlayerWeaponEntity playerWeaponEntity = new PlayerWeaponEntity();
        playerWeaponEntity.setWeaponId(this.packet.readInt());
        playerWeaponEntity.setUserId(this.packet.readInt());
        playerWeaponEntity.setAmmoRemaining(weapon.getAmmoCapacity());

        PlayerWeaponService.getInstance().update(playerWeaponEntity.getId(), PlayerWeaponMapper.toModel(playerWeaponEntity));

        List<PlayerWeaponModel> playerWeaponModels = PlayerWeaponService.getInstance().getByUserID(playerWeaponEntity.getUserId());
        this.client.sendResponse(new PlayerWeaponListComposer(playerWeaponModels));
    }
}
