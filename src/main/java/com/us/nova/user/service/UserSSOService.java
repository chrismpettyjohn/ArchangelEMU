package com.us.nova.user.service;

import com.us.nova.core.GenericService;
import com.us.nova.user.context.UserSSOContext;
import com.us.nova.user.entity.UserSSOEntity;
import com.us.nova.user.mapper.UserSSOMapper;
import com.us.nova.user.model.UserSSOModel;
import com.us.nova.user.repository.UserSSORepository;

import java.util.List;

public class UserSSOService extends GenericService<UserSSOModel, UserSSOContext, UserSSORepository> {
    private static UserSSOService instance;

    public static UserSSOService getInstance() {
        if (instance == null) {
            instance = new UserSSOService();
        }
        return instance;
    }

    private UserSSOService() {
        super(UserSSOContext.getInstance(), UserSSORepository.getInstance(), UserSSOMapper.class, UserSSOEntity.class);
    }

    public UserSSOModel create(UserSSOModel model) {
        return super.create(model);
    }

    public void update(int id, UserSSOModel model) {
        super.update(id, model);
    }

    public List<UserSSOModel> getAll() {
        return super.getAll();
    }

    public UserSSOModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
