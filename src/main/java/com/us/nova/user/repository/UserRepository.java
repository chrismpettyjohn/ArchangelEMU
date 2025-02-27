package com.us.nova.user.repository;

import com.us.archangel.gang.entity.GangRoleEntity;
import com.us.nova.core.GenericRepository;
import com.us.nova.user.entity.UserEntity;
import com.us.nova.user.model.UserModel;
import org.hibernate.Session;

import java.util.List;

public class UserRepository extends GenericRepository<UserEntity>  {

    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        super(UserEntity.class);
    }

    public UserEntity create(UserEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, UserEntity entity) {
        super.updateById(id, entity);
    }

    public UserEntity getById(int id) {
        return super.getById(id);
    }

    public List<UserEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
