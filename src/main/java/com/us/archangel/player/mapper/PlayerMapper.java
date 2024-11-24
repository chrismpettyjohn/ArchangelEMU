package com.us.archangel.player.mapper;

import com.us.archangel.player.entity.PlayerEntity;
import com.us.archangel.player.model.PlayerModel;
import com.us.nova.core.GenericMapper;

public class PlayerMapper extends GenericMapper<PlayerEntity, PlayerModel> {

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
                entity.getEscortingPlayerId(),
                entity.getCurrentAction(),
                null
        );
    }

    public static PlayerEntity toEntity(PlayerModel model) {
        if (model == null) {
            return null;
        }
        PlayerEntity entity = new PlayerEntity();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        entity.setGangId(model.getGangId());
        entity.setGangRoleId(model.getGangRoleId());
        entity.setCorpId(model.getCorpId());
        entity.setCorpRoleId(model.getCorpRoleId());
        entity.setHealthNow(model.getHealthNow());
        entity.setHealthMax(model.getHealthMax());
        entity.setEnergyNow(model.getEnergyNow());
        entity.setEnergyMax(model.getEnergyMax());
        entity.setArmorNow(model.getArmorNow());
        entity.setArmorMax(model.getArmorMax());
        entity.setHungerNow(model.getHungerNow());
        entity.setHungerMax(model.getHungerMax());
        entity.setLastPosX(model.getLastPosX());
        entity.setLastPosY(model.getLastPosY());
        entity.setWorkTimeRemainingSecs(model.getWorkTimeRemainingSecs());
        entity.setCombatDelayRemainingSecs(model.getCombatDelayExpiresAt());
        entity.setJailTimeRemainingSecs(model.getJailTimeRemainingSecs());
        entity.setCurrentAction(model.getCurrentAction());
        entity.setEscortingPlayerId(model.getEscortingPlayerId());
        return entity;
    }
}
