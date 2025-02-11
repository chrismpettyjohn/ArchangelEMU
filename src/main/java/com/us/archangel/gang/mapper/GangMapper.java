package com.us.archangel.gang.mapper;

import com.us.archangel.gang.entity.GangEntity;
import com.us.archangel.gang.model.GangModel;
import com.us.nova.core.GenericMapper;

public class GangMapper extends GenericMapper<GangEntity, GangModel> {

    public static GangModel toModel(GangEntity entity) {
        if (entity == null) {
            return null;
        }
        return new GangModel(
                entity.getId(),
                entity.getDisplayName(),
                entity.getDescription(),
                entity.getBadge(),
                entity.getColorPrimary(),
                entity.getColorSecondary(),
                entity.getUserId(),
                entity.getRoomId()
        );
    }

    public static GangEntity toEntity(GangModel model) {
        if (model == null) {
            return null;
        }
        GangEntity entity = new GangEntity();
        entity.setId(model.getId());
        entity.setDisplayName(model.getDisplayName());
        entity.setDescription(model.getDescription());
        entity.setBadge(model.getBadge());
        entity.setColorPrimary(model.getColorPrimary());
        entity.setColorSecondary(model.getColorSecondary());
        entity.setUserId(model.getUserId());
        entity.setRoomId(model.getRoomId());
        return entity;
    }
}
