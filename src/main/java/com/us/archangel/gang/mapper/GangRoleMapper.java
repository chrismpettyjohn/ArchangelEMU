package com.us.archangel.gang.mapper;

import com.us.archangel.gang.entity.GangRoleEntity;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.nova.core.GenericMapper;

public class GangRoleMapper extends GenericMapper<GangRoleEntity, GangRoleModel> {

    public static GangRoleModel toModel(GangRoleEntity entity) {
        if (entity == null) {
            return null;
        }
        return new GangRoleModel(
                entity.getId(),
                entity.getGangId(),
                entity.getOrderId(),
                entity.getName(),
                entity.getCanInvite(),
                entity.getCanKick()
        );
    }

    public static GangRoleEntity toEntity(GangRoleModel model) {
        if (model == null) {
            return null;
        }
        GangRoleEntity entity = new GangRoleEntity();
        entity.setId(model.getId());
        entity.setGangId(model.getGangId());
        entity.setOrderId(model.getOrderId());
        entity.setName(model.getName());
        entity.setCanInvite(model.isCanInvite());
        entity.setCanKick(model.isCanKick());
        return entity;
    }
}
