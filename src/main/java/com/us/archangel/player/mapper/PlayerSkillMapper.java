package com.us.archangel.player.mapper;

import com.us.archangel.player.entity.PlayerSkillEntity;
import com.us.archangel.player.model.PlayerSkillModel;

public class PlayerSkillMapper {

    public static PlayerSkillModel toModel(PlayerSkillEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PlayerSkillModel(
                entity.getId(),
                entity.getUserId(),
                entity.getStrengthXp(),
                entity.getLumberjackXp(),
                entity.getMeleeXp(),
                entity.getWeaponXp(),
                entity.getFarmingXp(),
                entity.getMiningXp(),
                entity.getFishingXp(),
                entity.getStaminaXp()
        );
    }

    public static PlayerSkillEntity toEntity(PlayerSkillModel model) {
        if (model == null) {
            return null;
        }
        PlayerSkillEntity entity = new PlayerSkillEntity();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        return entity;
    }
}