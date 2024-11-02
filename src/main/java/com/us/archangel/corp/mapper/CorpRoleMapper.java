package com.us.archangel.corp.mapper;

import com.us.archangel.corp.entity.CorpRoleEntity;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.nova.core.GenericMapper;

public class CorpRoleMapper extends GenericMapper<CorpRoleEntity, CorpRoleModel> {

    public static CorpRoleModel toModel(CorpRoleEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CorpRoleModel(
                entity.getId(),
                entity.getCorpId(),
                entity.getOrderId(),
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
        entity.setCorpId(model.getCorpId());
        entity.setOrderId(model.getOrderId());
        entity.setName(model.getDisplayName());
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
