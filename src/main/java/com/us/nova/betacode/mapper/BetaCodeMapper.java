package com.us.nova.betacode.mapper;

import com.us.nova.betacode.entity.BetaCodeEntity;
import com.us.nova.betacode.model.BetaCodeModel;

public class BetaCodeMapper {

    public static BetaCodeModel toModel(BetaCodeEntity entity) {
        if (entity == null) {
            return null;
        }
        return new BetaCodeModel(
                entity.getId(),
                entity.getCode(),
                entity.getUserId(),
                entity.getClaimedAt(),
                entity.getCreatedAt()
        );
    }

    public static BetaCodeEntity toEntity(BetaCodeModel model) {
        if (model == null) {
            return null;
        }
        BetaCodeEntity entity = new BetaCodeEntity();
        entity.setId(model.getId());
        entity.setCode(model.getCode());
        entity.setUserId(model.getClaimedByUserId());
        entity.setClaimedAt(model.getClaimedAt());
        entity.setCreatedAt(model.getCreatedAt());
        return entity;
    }
}