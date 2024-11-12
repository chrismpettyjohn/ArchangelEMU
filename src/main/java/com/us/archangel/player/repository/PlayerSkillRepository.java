package com.us.archangel.player.repository;

import com.us.archangel.player.entity.PlayerSkillEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class PlayerSkillRepository extends GenericRepository<PlayerSkillEntity> {

    private static PlayerSkillRepository instance;

    public static synchronized PlayerSkillRepository getInstance() {
        if (instance == null) {
            instance = new PlayerSkillRepository();
        }
        return instance;
    }

    private PlayerSkillRepository() {
        super(PlayerSkillEntity.class);
    }

    public PlayerSkillEntity create(PlayerSkillEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, PlayerSkillEntity entity) {
        super.updateById(id, entity);
    }

    public PlayerSkillEntity getById(int id) {
        return super.getById(id);
    }

    public List<PlayerSkillEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

    public PlayerSkillEntity getByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerSkillEntity where userId = :userId", PlayerSkillEntity.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        }
    }
}
