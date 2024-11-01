package com.us.archangel.player.mapper;

import com.us.archangel.player.entity.PlayerWeaponEntity;
import com.us.archangel.player.model.PlayerWeaponModel;

public class PlayerWeaponMapper {

    public static PlayerWeaponModel toModel(PlayerWeaponEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PlayerWeaponModel(
                entity.getId(),
                entity.getUserId(),
                entity.getWeaponId(),
                entity.getAmmoRemaining()
        );
    }

    public static PlayerWeaponEntity toEntity(PlayerWeaponModel model) {
        if (model == null) {
            return null;
        }
        PlayerWeaponEntity entity = new PlayerWeaponEntity();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        entity.setWeaponId(model.getWeaponId());
        entity.setAmmoRemaining(model.getAmmoRemaining());
        return entity;
    }
}