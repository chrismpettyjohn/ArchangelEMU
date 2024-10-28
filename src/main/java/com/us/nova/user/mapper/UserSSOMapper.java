package com.us.nova.user.mapper;

import com.us.nova.user.entity.UserSSOEntity;
import com.us.nova.user.model.UserSSOModel;

public class UserSSOMapper {

    public static UserSSOModel toModel(UserSSOEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserSSOModel(
                entity.getId(),
                entity.getUserId(),
                entity.getSsoToken(),
                entity.getExpiresAt(),
                entity.getActivatedAt(),
                entity.getIpAddress()
        );
    }

    public static UserSSOEntity toEntity(UserSSOModel model) {
        if (model == null) {
            return null;
        }
        UserSSOEntity entity = new UserSSOEntity();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        entity.setSsoToken(model.getSsoToken());
        entity.setExpiresAt(model.getExpiresAt());
        entity.setActivatedAt(model.getActivatedAt());
        entity.setIpAddress(model.getIpAddress());
        return entity;
    }
}