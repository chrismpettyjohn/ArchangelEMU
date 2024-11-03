package com.us.nova.user.repository;

import com.us.nova.core.GenericRepository;
import com.us.nova.user.entity.UserSSOEntity;

import java.util.List;

public class UserSSORepository extends GenericRepository<UserSSOEntity> {

    private static UserSSORepository instance;

    public static UserSSORepository getInstance() {
        if (instance == null) {
            instance = new UserSSORepository();
        }
        return instance;
    }

    private UserSSORepository() {
        super(UserSSOEntity.class);
    }

    public void create(UserSSOEntity entity) {
        super.create(entity);
    }

    public void updateById(int id, UserSSOEntity entity) {
        super.updateById(id, entity);
    }

    public UserSSOEntity getById(int id) {
        return super.getById(id);
    }

    public List<UserSSOEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
