package com.us.archangel.crime.mapper;

import com.us.archangel.crime.entity.CrimeEntity;
import com.us.archangel.crime.model.CrimeModel;

public class CrimeMapper {

    public static CrimeModel toModel(CrimeEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CrimeModel(
                entity.getId(),
                entity.getDisplayName(),
                entity.getDescription(),
                entity.getJailTimeSeconds()
        );
    }

    public static CrimeEntity toEntity(CrimeModel model) {
        if (model == null) {
            return null;
        }
        CrimeEntity entity = new CrimeEntity();
        entity.setId(model.getId());
        entity.setDisplayName(model.getDisplayName());
        entity.setDescription(model.getDescription());
        entity.setJailTimeSeconds(model.getJailTimeSeconds());
        return entity;
    }
}