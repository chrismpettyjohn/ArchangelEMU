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
                entity.getDescription(),
                entity.getMotto(),
                entity.getSalary(),
                entity.getMaleFigure(),
                entity.getFemaleFigure(),
                entity.getCanHire() == 1,
                entity.getCanFire() == 1,
                entity.getCanPromote() == 1,
                entity.getCanDemote() == 1,
                entity.getCanWorkAnywhere() == 1
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
        entity.setDescription(model.getDescription());
        entity.setMotto(model.getMotto());
        entity.setSalary(model.getSalary());
        entity.setMaleFigure(model.getMaleFigure());
        entity.setFemaleFigure(model.getFemaleFigure());
        entity.setCanHire(model.isCanHire() ? 1 : 0);
        entity.setCanFire(model.isCanFire() ? 1 : 0);
        entity.setCanPromote(model.isCanPromote() ? 1 : 0);
        entity.setCanDemote(model.isCanDemote() ? 1 : 0);
        entity.setCanWorkAnywhere(model.isCanWorkAnywhere() ? 1 : 0);
        return entity;
    }
}
