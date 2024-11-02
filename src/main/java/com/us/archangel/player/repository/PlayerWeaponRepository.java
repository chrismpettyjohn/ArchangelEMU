package com.us.archangel.player.repository;

import com.us.archangel.player.entity.PlayerWeaponEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class PlayerWeaponRepository extends GenericRepository<PlayerWeaponEntity> {

    private static PlayerWeaponRepository instance;

    public static synchronized PlayerWeaponRepository getInstance() {
        if (instance == null) {
            instance = new PlayerWeaponRepository();
        }
        return instance;
    }

    private PlayerWeaponRepository() {
        super(PlayerWeaponEntity.class);
    }

    public List<PlayerWeaponEntity> getByPlayerId(int playerId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerWeaponEntity where userId = :playerId", PlayerWeaponEntity.class)
                    .setParameter("playerId", playerId)
                    .list();
        }
    }
}
