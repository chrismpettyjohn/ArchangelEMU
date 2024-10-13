package com.eu.archangel.corp.mapper;

import com.eu.archangel.corp.entity.CorpEntity;
import com.eu.archangel.corp.model.CorpModel;

public class CorpMapper {

    public static CorpModel toModel(CorpEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CorpModel(
                entity.getId(),
                entity.getDisplayName(),
                entity.getSector(),
                entity.getCorpIndustry(),
                entity.getUserId(),
                entity.getRoomId()
        );
    }

    public static CorpEntity toEntity(CorpModel model) {
        if (model == null) {
            return null;
        }
        CorpEntity entity = new CorpEntity();
        entity.setId(model.getId());
        entity.setDisplayName(model.getDisplayName());
        entity.setSector(model.getSector());
        entity.setCorpIndustry(model.getIndustry());
        entity.setUserId(model.getUserId());
        entity.setRoomId(model.getRoomId());
        return entity;
    }
}