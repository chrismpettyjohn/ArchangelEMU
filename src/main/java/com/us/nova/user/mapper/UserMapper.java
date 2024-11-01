package com.us.nova.user.mapper;

import com.us.nova.user.entity.UserEntity;
import com.us.nova.user.entity.UserSSOEntity;
import com.us.nova.user.model.UserModel;
import com.us.nova.user.model.UserSSOModel;

public class UserMapper {

    public static UserModel toModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserModel(
                entity.getId()
        );
    }

    public static UserEntity toEntity(UserModel model) {
        if (model == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        return entity;
    }
}