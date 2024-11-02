package com.us.archangel.gang.mapper;

import com.us.archangel.gang.entity.GangInviteEntity;
import com.us.archangel.gang.model.GangInviteModel;
import com.us.nova.core.GenericMapper;

public class GangInviteMapper extends GenericMapper<GangInviteEntity, GangInviteModel> {

    public static GangInviteModel toModel(GangInviteEntity entity) {
        if (entity == null) {
            return null;
        }
        return new GangInviteModel(
                entity.getId(),
                entity.getGangId(),
                entity.getGangRoleId(),
                entity.getUserId()
        );
    }

    public static GangInviteEntity toEntity(GangInviteModel model) {
        if (model == null) {
            return null;
        }
        GangInviteEntity entity = new GangInviteEntity();
        entity.setId(model.getId());
        entity.setGangId(model.getGangId());
        entity.setGangRoleId(model.getGangRoleId());
        entity.setUserId(model.getUserId());
        return entity;
    }
}
