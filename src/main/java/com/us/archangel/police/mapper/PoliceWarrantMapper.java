package com.us.archangel.police.mapper;

import com.us.archangel.police.entity.PoliceCrimeEntity;
import com.us.archangel.police.model.PoliceCrimeModel;
import com.us.nova.core.GenericMapper;

public class PoliceWarrantMapper extends GenericMapper<PoliceCrimeEntity, PoliceCrimeModel> {

    public static PoliceCrimeModel toModel(PoliceCrimeEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PoliceCrimeModel(
                entity.getId(),
                entity.getDisplayName(),
                entity.getDescription(),
                entity.getJailTimeSeconds()
        );
    }

    public static PoliceCrimeEntity toEntity(PoliceCrimeModel model) {
        if (model == null) {
            return null;
        }
        PoliceCrimeEntity entity = new PoliceCrimeEntity();
        entity.setId(model.getId());
        entity.setDisplayName(model.getDisplayName());
        entity.setDescription(model.getDescription());
        entity.setJailTimeSeconds(model.getJailTimeSeconds());
        return entity;
    }
}
