package com.us.archangel.feature.weapon.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.weapon.entity.WeaponEntity;
import com.us.archangel.weapon.mapper.WeaponMapper;
import com.us.archangel.weapon.model.WeaponPermissions;
import com.us.archangel.weapon.service.WeaponService;

public class WeaponCreateEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canCreateWeapons = this.client.getHabbo().hasPermissionRight(WeaponPermissions.CREATE);

        if (!canCreateWeapons) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        WeaponEntity weaponEntity = new WeaponEntity();
        weaponEntity.setUniqueName(this.packet.readString());
        weaponEntity.setDisplayName(this.packet.readString());
        weaponEntity.setMinDamage(this.packet.readInt());
        weaponEntity.setMaxDamage(this.packet.readInt());
        weaponEntity.setRangeInTiles(this.packet.readInt());
        weaponEntity.setAccuracy(this.packet.readInt());
        weaponEntity.setReloadTime(this.packet.readInt());
        weaponEntity.setReloadMessage(this.packet.readString());
        weaponEntity.setAmmoCapacity(this.packet.readInt());
        weaponEntity.setWeight(this.packet.readInt());
        weaponEntity.setCooldownSeconds(this.packet.readInt());
        weaponEntity.setEquipEffect(this.packet.readInt());
        weaponEntity.setEquipMessage(this.packet.readString());
        weaponEntity.setUnequipMessage(this.packet.readString());
        weaponEntity.setAttackMessage(this.packet.readString());

        WeaponService.getInstance().create(WeaponMapper.toModel(weaponEntity));
    }
}
