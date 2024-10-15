package com.us.archangel.penalty.mapper;


import com.us.archangel.penalty.entity.PenaltyEntity;
import com.us.archangel.penalty.model.PenaltyModel;

public class PenaltyMapper {

    public static PenaltyModel toModel(PenaltyEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PenaltyModel(
                entity.getId(),
                entity.getDisplayName(),
                entity.getDescription(),
                entity.getIsCriminal(),
                entity.getCriminalSentenceInSeconds(),
                entity.getIsCivil(),
                entity.getCivilFineInCredits()
        );
    }

    public static PenaltyEntity toEntity(PenaltyModel model) {
        if (model == null) {
            return null;
        }
        PenaltyEntity entity = new PenaltyEntity();
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