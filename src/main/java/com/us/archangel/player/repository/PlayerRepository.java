package com.us.archangel.player.repository;

import com.us.archangel.player.entity.PlayerEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class PlayerRepository extends GenericRepository<PlayerEntity> {

    private static PlayerRepository instance;

    public static synchronized PlayerRepository getInstance() {
        if (instance == null) {
            instance = new PlayerRepository();
        }
        return instance;
    }

    private PlayerRepository() {
        super(PlayerEntity.class);
    }

    public void create(PlayerEntity entity) {
        super.create(entity);
    }

    public void updateById(int id, PlayerEntity entity) {
        super.updateById(id, entity);
    }

    public PlayerEntity getById(int id) {
        return super.getById(id);
    }

    public List<PlayerEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

    public PlayerEntity getByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerEntity where userId = :userId", PlayerEntity.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        }
    }

    public List<PlayerEntity> getByCorpId(int corpId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerEntity where corpId = :corpId", PlayerEntity.class)
                    .setParameter("corpId", corpId)
                    .list();
        }
    }

    public List<PlayerEntity> getByCorpRoleId(int corpRoleID) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerEntity where corpRoleId = :corpRoleID", PlayerEntity.class)
                    .setParameter("corpRoleID", corpRoleID)
                    .list();
        }
    }
}
