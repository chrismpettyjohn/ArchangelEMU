package com.us.archangel.government.mapper;

import com.us.archangel.government.entity.GovernmentLawEntity;
import com.us.archangel.government.model.GovernmentLawModel;
import com.us.nova.core.GenericMapper;

public class GovernmentLawMapper extends GenericMapper<GovernmentLawEntity, GovernmentLawModel> {

    public static GovernmentLawModel toModel(GovernmentLawEntity entity) {
        if (entity == null) {
            return null;
        }
        return new GovernmentLawModel(
                entity.getId(),
                entity.getDisplayName(),
                entity.getDescription(),
                entity.getContent(),
                entity.getProposedByUserId(),
                entity.getStatus(),
                entity.getType()
        );
    }

    public static GovernmentLawEntity toEntity(GovernmentLawModel model) {
        if (model == null) {
            return null;
        }
        GovernmentLawEntity entity = new GovernmentLawEntity();
        entity.setId(model.getId());
        entity.setDisplayName(model.getDisplayName());
        entity.setDescription(model.getDescription());
        entity.setContent(model.getContent());
        entity.setProposedByUserId(model.getProposedByUserId());
        entity.setStatus(model.getStatus());
        entity.setType(model.getType());
        return entity;
    }
}
