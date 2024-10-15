package com.eu.archangel.corp.mapper;

import com.eu.archangel.corp.entity.CorpRoleEntity;
import com.eu.archangel.corp.model.CorpRoleModel;

public class CorpRoleMapper {

    public static CorpRoleModel toModel(CorpRoleEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CorpRoleModel(
                entity.getId(),
                entity.getCorpID(),
                entity.getOrderID(),
                entity.getName(),
                entity.getMotto(),
                entity.getSalary(),
                entity.getMaleFigure(),
                entity.getFemaleFigure(),
                entity.isCanHire(),
                entity.isCanFire(),
                entity.isCanPromote(),
                entity.isCanDemote(),
                entity.isCanWorkAnywhere()
        );
    }

    public static CorpRoleEntity toEntity(CorpRoleModel model) {
        if (model == null) {
            return null;
        }
        CorpRoleEntity entity = new CorpRoleEntity();
        entity.setId(model.getId());
        entity.setCorpID(model.getCorpId());
        entity.setOrderID(model.getOrderId());
        entity.setName(model.getName());
        entity.setMotto(model.getMotto());
        entity.setSalary(model.getSalary());
        entity.setMaleFigure(model.getMaleFigure());
        entity.setFemaleFigure(model.getFemaleFigure());
        entity.setCanHire(model.isCanHire());
        entity.setCanFire(model.isCanFire());
        entity.setCanPromote(model.isCanPromote());
        entity.setCanDemote(model.isCanDemote());
        entity.setCanWorkAnywhere(model.isCanWorkAnywhere());
        return entity;
    }
}
