package com.us.archangel.player.mapper;

import com.us.archangel.player.entity.PlayerKillHistoryEntity;
import com.us.archangel.player.model.PlayerKillHistoryModel;
import com.us.nova.core.GenericMapper;

public class PlayerKillHistoryMapper extends GenericMapper<PlayerKillHistoryEntity, PlayerKillHistoryModel> {

    public static PlayerKillHistoryModel toModel(PlayerKillHistoryEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PlayerKillHistoryModel(
                entity.getId(),
                entity.getAttackerUserId(),
                entity.getVictimUserId(),
                entity.getCreatedAt()
        );
    }

    public static PlayerKillHistoryEntity toEntity(PlayerKillHistoryModel model) {
        if (model == null) {
            return null;
        }
        PlayerKillHistoryEntity entity = new PlayerKillHistoryEntity();
        entity.setId(model.getId());
        entity.setAttackerUserId(model.getAttackerUserId());
        entity.setVictimUserId(model.getVictimUserId());
        entity.setCreatedAt(model.getCreatedAt());
        return entity;
    }
}
