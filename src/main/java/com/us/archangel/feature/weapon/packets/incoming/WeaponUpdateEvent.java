package com.us.archangel.feature.weapon.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.weapon.packets.outgoing.WeaponDataComposer;
import com.us.archangel.feature.weapon.packets.outgoing.WeaponListComposer;
import com.us.archangel.weapon.entity.WeaponEntity;
import com.us.archangel.weapon.mapper.WeaponMapper;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.model.WeaponPermissions;
import com.us.archangel.weapon.service.WeaponService;

public class WeaponUpdateEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canUpdateWeapons = this.client.getHabbo().hasPermissionRight(WeaponPermissions.UPDATE);

        if (!canUpdateWeapons) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        WeaponModel weapon = WeaponService.getInstance().getById(this.packet.readInt());

        WeaponEntity weaponEntity = WeaponMapper.toEntity(weapon);
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

        WeaponModel weaponModel = WeaponMapper.toModel(weaponEntity);

        WeaponService.getInstance().update(weapon.getId(), weaponModel);

        this.client.sendResponse(new WeaponDataComposer(WeaponMapper.toModel(weaponEntity)));
    }
}
