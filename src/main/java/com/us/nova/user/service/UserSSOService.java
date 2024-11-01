package com.us.nova.user.service;

import com.us.nova.user.context.UserSSOContext;
import com.us.nova.user.entity.UserSSOEntity;
import com.us.nova.user.mapper.UserSSOMapper;
import com.us.nova.user.repository.UserSSORepository;

public class UserSSOService {
    private static UserSSOService instance;

    public static UserSSOService getInstance() {
        if (instance == null) {
            instance = new UserSSOService();
        }
        return instance;
    }

    public void create(UserSSOEntity userSSOEntity) {
        UserSSOContext.getInstance().add(userSSOEntity.getId(), UserSSOMapper.toModel(userSSOEntity));
        UserSSORepository.getInstance().create(userSSOEntity);
    }

    public void update(int id, UserSSOEntity userSSOEntity) {
        UserSSOContext.getInstance().update(id, UserSSOMapper.toModel(userSSOEntity));
        UserSSORepository.getInstance().updateById(id, userSSOEntity);
    }

    public void deleteById(int id) {
        UserSSOContext.getInstance().delete(id);
        UserSSORepository.getInstance().deleteById(id);
    }
}
