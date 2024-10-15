package com.us.archangel.bounty.mapper;

import com.us.archangel.bounty.entity.BountyEntity;
import com.us.archangel.bounty.model.BountyModel;

public class BountyMapper {

    public static BountyModel toModel(BountyEntity entity) {
        if (entity == null) {
            return null;
        }
        return new BountyModel(
                entity.getId(),
                entity.getUserId(),
                entity.getCrimeId()
        );
    }

    public static BountyEntity toEntity(BountyModel model) {
        if (model == null) {
            return null;
        }
        BountyEntity entity = new BountyEntity();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        entity.setCrimeId(model.getCrimeId());
        return entity;
    }
}