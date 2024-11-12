package com.us.archangel.corp.mapper;

import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.model.CorpModel;
import com.us.nova.core.GenericMapper;

public class CorpMapper extends GenericMapper<CorpEntity, CorpModel> {

    public static CorpModel toModel(CorpEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CorpModel(
                entity.getId(),
                entity.getDisplayName(),
                entity.getDescription(),
                entity.getBadge(),
                entity.getSector(),
                entity.getIndustry(),
                entity.getUserId(),
                entity.getRoomId(),
                entity.getCreatedAt()
        );
    }

    public static CorpEntity toEntity(CorpModel model) {
        if (model == null) {
            return null;
        }
        CorpEntity entity = new CorpEntity();
        entity.setId(model.getId());
        entity.setDisplayName(model.getDisplayName());
        entity.setDescription(model.getDescription());
        entity.setBadge(model.getBadge());
        entity.setSector(model.getSector());
        entity.setIndustry(model.getIndustry());
        entity.setUserId(model.getUserId());
        entity.setRoomId(model.getRoomId());
        entity.setCreatedAt(model.getCreatedAt());
        return entity;
    }
}