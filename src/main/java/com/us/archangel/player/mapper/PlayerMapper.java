package com.us.archangel.player.mapper;

import com.us.archangel.player.entity.PlayerEntity;
import com.us.archangel.player.model.PlayerModel;

public class PlayerMapper {

    public static PlayerModel toModel(PlayerEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PlayerModel(
                entity.getId(),
                entity.getUserId()
        );
    }

    public static PlayerEntity toEntity(PlayerModel model) {
        if (model == null) {
            return null;
        }
        PlayerEntity entity = new PlayerEntity();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        return entity;
    }
}