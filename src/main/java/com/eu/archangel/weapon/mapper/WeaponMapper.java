package com.eu.archangel.weapon.mapper;

import com.eu.archangel.weapon.entity.WeaponEntity;
import com.eu.archangel.weapon.model.WeaponModel;

public class WeaponMapper {

    public static WeaponModel toModel(WeaponEntity entity) {
        if (entity == null) {
            return null;
        }
        return new WeaponModel(
                entity.getId(),
                entity.getDisplayName(),
                entity.getUniqueName(),
                entity.getType(),
                entity.getMinDamage(),
                entity.getMaxDamage(),
                entity.getRangeInTiles(),
                entity.getAccuracy(),
                entity.getReloadTime(),
                entity.getReloadMessage(),
                entity.getAmmoCapacity(),
                entity.getWeight(),
                entity.getCooldownSeconds(),
                entity.getSpecialAbilities(),
                entity.getEquipEffect(),
                entity.getEquipMessage(),
                entity.getUnequipMessage(),
                entity.getAttackMessage()
        );
    }

    public static WeaponEntity toEntity(WeaponModel model) {
        if (model == null) {
            return null;
        }
        WeaponEntity entity = new WeaponEntity();
        entity.setId(model.getId());
        entity.setDisplayName(model.getDisplayName());
        entity.setUniqueName(model.getUniqueName());
        entity.setType(model.getType());
        entity.setMinDamage(model.getMinDamage());
        entity.setMaxDamage(model.getMaxDamage());
        entity.setRangeInTiles(model.getRangeInTiles());
        entity.setAccuracy(model.getAccuracy());
        entity.setReloadTime(model.getReloadTime());
        entity.setReloadMessage(model.getReloadMessage());
        entity.setAmmoCapacity(model.getAmmoCapacity());
        entity.setWeight(model.getWeight());
        entity.setCooldownSeconds(model.getCooldownSeconds());
        entity.setSpecialAbilities(model.getSpecialAbilities());
        entity.setEquipEffect(model.getEquipEffect());
        entity.setEquipMessage(model.getEquipMessage());
        entity.setUnequipMessage(model.getUnequipMessage());
        entity.setAttackMessage(model.getAttackMessage());
        return entity;
    }
}
