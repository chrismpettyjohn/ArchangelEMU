package com.us.archangel.ammo.mapper;

import com.us.archangel.ammo.entity.AmmoEntity;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.nova.core.GenericMapper;

public class AmmoMapper extends GenericMapper<AmmoEntity, AmmoModel> {

    public static AmmoModel toModel(AmmoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new AmmoModel(
                entity.getId(),
                entity.getDisplayName(),
                entity.getUniqueName(),
                entity.getSize(),
                entity.getType()
        );
    }

    public static AmmoEntity toEntity(AmmoModel model) {
        if (model == null) {
            return null;
        }
        AmmoEntity entity = new AmmoEntity();
        entity.setId(model.getId());
        entity.setDisplayName(model.getDisplayName());
        entity.setUniqueName(model.getUniqueName());
        entity.setSize(model.getSize());
        entity.setType(model.getType());
        return entity;
    }
}
