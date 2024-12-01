package com.us.archangel.player.mapper;

import com.us.archangel.player.entity.PlayerAmmoEntity;
import com.us.archangel.player.model.PlayerAmmoModel;
import com.us.nova.core.GenericMapper;

public class PlayerAmmoMapper extends GenericMapper<PlayerAmmoEntity, PlayerAmmoModel> {

    public static PlayerAmmoModel toModel(PlayerAmmoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PlayerAmmoModel(
                entity.getId(),
                entity.getUserId(),
                entity.getAmmoId(),
                entity.getAmmoRemaining()
        );
    }

    public static PlayerAmmoEntity toEntity(PlayerAmmoModel model) {
        if (model == null) {
            return null;
        }
        PlayerAmmoEntity entity = new PlayerAmmoEntity();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        entity.setAmmoId(model.getAmmoId());
        entity.setAmmoRemaining(model.getAmmoRemaining());
        return entity;
    }
}
