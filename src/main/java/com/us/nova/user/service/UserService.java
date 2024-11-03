package com.us.nova.user.service;

import com.us.nova.core.GenericService;
import com.us.nova.user.context.UserContext;
import com.us.nova.user.entity.UserEntity;
import com.us.nova.user.mapper.UserMapper;
import com.us.nova.user.model.UserModel;
import com.us.nova.user.repository.UserRepository;

import java.util.List;

public class UserService extends GenericService<UserModel, UserContext, UserRepository> {

    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {
        super(UserContext.getInstance(), UserRepository.getInstance(), UserMapper.class, UserEntity.class);
    }

    public void create(UserModel model) {
        super.create(model);
    }

    public void update(int id, UserModel model) {
        super.update(id, model);
    }

    public List<UserModel> getAll() {
        return super.getAll();
    }

    public UserModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
