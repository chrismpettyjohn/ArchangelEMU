package com.us.archangel.corp.mapper;

import com.us.archangel.corp.entity.CorpInviteEntity;
import com.us.archangel.corp.model.CorpInviteModel;
import com.us.nova.core.GenericMapper;

public class CorpInviteMapper extends GenericMapper<CorpInviteEntity, CorpInviteModel> {

    public static CorpInviteModel toModel(CorpInviteEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CorpInviteModel(
                entity.getId(),
                entity.getCorpId(),
                entity.getCorpRoleId(),
                entity.getUserId()
        );
    }

    public static CorpInviteEntity toEntity(CorpInviteModel model) {
        if (model == null) {
            return null;
        }
        CorpInviteEntity entity = new CorpInviteEntity();
        entity.setId(model.getId());
        entity.setCorpId(model.getCorpId());
        entity.setCorpId(model.getCorpRoleId());
        entity.setCorpId(model.getUserId());
        return entity;
    }
}
