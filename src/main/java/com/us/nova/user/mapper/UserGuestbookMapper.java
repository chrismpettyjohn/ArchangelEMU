package com.us.nova.user.mapper;

import com.us.nova.user.entity.UserGuestbookEntity;
import com.us.nova.user.model.UserGuestbookModel;

public class UserGuestbookMapper {

    public static UserGuestbookModel toModel(UserGuestbookEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserGuestbookModel(
                entity.getId(),
                entity.getPostedOnUsersId(),
                entity.getPostedByUsersId(),
                entity.getMessage(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static UserGuestbookEntity toEntity(UserGuestbookModel model) {
        if (model == null) {
            return null;
        }
        UserGuestbookEntity entity = new UserGuestbookEntity();
        entity.setId(model.getId());
        entity.setPostedOnUsersId(model.getPostedOnUsersId());
        entity.setPostedByUsersId(model.getPostedByUsersId());
        entity.setMessage(model.getMessage());
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());
        return entity;
    }
}