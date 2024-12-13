package com.us.archangel.player.repository;

import com.us.archangel.player.entity.PlayerKillHistoryEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class PlayerKillHistoryRepository extends GenericRepository<PlayerKillHistoryEntity> {

    private static PlayerKillHistoryRepository instance;

    public static synchronized PlayerKillHistoryRepository getInstance() {
        if (instance == null) {
            instance = new PlayerKillHistoryRepository();
        }
        return instance;
    }

    private PlayerKillHistoryRepository() {
        super(PlayerKillHistoryEntity.class);
    }

    public PlayerKillHistoryEntity create(PlayerKillHistoryEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, PlayerKillHistoryEntity entity) {
        super.updateById(id, entity);
    }

    public PlayerKillHistoryEntity getById(int id) {
        return super.getById(id);
    }

    public List<PlayerKillHistoryEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

    public List<PlayerKillHistoryEntity> getKillsBetweenTwoPeople(int userId1, int userId2) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "from PlayerKillHistoryEntity where " +
                                    "(attackerUserId = :userId1 and victimUserId = :userId2) or " +
                                    "(attackerUserId = :userId2 and victimUserId = :userId1)",
                            PlayerKillHistoryEntity.class)
                    .setParameter("userId1", userId1)
                    .setParameter("userId2", userId2)
                    .list();
        }
    }

    public List<PlayerKillHistoryEntity> getByAttackerUserId(int attackerUserId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerKillHistoryEntity where attackerUserId = :attackerUserId", PlayerKillHistoryEntity.class)
                    .setParameter("attackerUserId", attackerUserId)
                    .list();
        }
    }

    public List<PlayerKillHistoryEntity> getByVictimUserId(int victimUserId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerKillHistoryEntity where victimUserId = :victimUserId", PlayerKillHistoryEntity.class)
                    .setParameter("victimUserId", victimUserId)
                    .list();
        }
    }
}
