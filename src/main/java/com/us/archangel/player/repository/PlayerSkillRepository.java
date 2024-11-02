package com.us.archangel.player.repository;

import com.us.archangel.player.entity.PlayerSkillEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

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

    public PlayerSkillEntity getByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerSkillEntity where userId = :userId", PlayerSkillEntity.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        }
    }
}
