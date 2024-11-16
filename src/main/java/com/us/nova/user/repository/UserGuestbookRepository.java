package com.us.nova.user.repository;

import com.us.nova.core.GenericRepository;
import com.us.nova.user.entity.UserGuestbookEntity;

import java.util.List;

public class UserGuestbookRepository extends GenericRepository<UserGuestbookEntity> {

    private static UserGuestbookRepository instance;

    public static UserGuestbookRepository getInstance() {
        if (instance == null) {
            instance = new UserGuestbookRepository();
        }
        return instance;
    }

    private UserGuestbookRepository() {
        super(UserGuestbookEntity.class);
    }

    public UserGuestbookEntity create(UserGuestbookEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, UserGuestbookEntity entity) {
        super.updateById(id, entity);
    }

    public UserGuestbookEntity getById(int id) {
        return super.getById(id);
    }

    public List<UserGuestbookEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
