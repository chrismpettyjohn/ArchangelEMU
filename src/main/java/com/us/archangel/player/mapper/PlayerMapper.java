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
                entity.getUserId(),
                entity.getGangId(),
                entity.getGangRoleId(),
                entity.getCorpId(),
                entity.getCorpRoleId(),
                entity.getHealthNow(),
                entity.getHealthMax(),
                entity.getEnergyNow(),
                entity.getEnergyMax(),
                entity.getArmorNow(),
                entity.getArmorMax(),
                entity.getHungerNow(),
                entity.getHungerMax(),
                entity.getLastPosX(),
                entity.getLastPosY(),
                entity.getWorkTimeRemainingSecs(),
                entity.getCombatDelayRemainingSecs(),
                entity.getJailTimeRemainingSecs(),
                entity.getCurrentAction(),
                entity.getEscortingPlayerId()
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