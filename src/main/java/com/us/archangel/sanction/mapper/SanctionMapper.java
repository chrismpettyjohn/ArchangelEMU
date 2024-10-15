package com.us.archangel.sanction.mapper;


import com.us.archangel.sanction.entity.SanctionEntity;
import com.us.archangel.sanction.model.SanctionModel;

public class SanctionMapper {

    public static SanctionModel toModel(SanctionEntity entity) {
        if (entity == null) {
            return null;
        }
        return new SanctionModel(
                entity.getId(),
                entity.getDisplayName(),
                entity.getDescription(),
                entity.getIsCriminal(),
                entity.getCriminalSentenceInSeconds(),
                entity.getIsCivil(),
                entity.getCivilFineInCredits()
        );
    }

    public static SanctionEntity toEntity(SanctionModel model) {
        if (model == null) {
            return null;
        }
        SanctionEntity entity = new SanctionEntity();
        entity.setId(model.getId());
        entity.setDisplayName(model.getDisplayName());
        entity.setDescription(model.getDescription());
        entity.setIsCriminal(model.getIsCriminal());
        entity.setCriminalSentenceInSeconds(model.getCriminalSentenceInSeconds());
        entity.setIsCivil(model.getIsCivil());
        entity.setCivilFineInCredits(model.getCivilFineInCredits());
        return entity;
    }
}