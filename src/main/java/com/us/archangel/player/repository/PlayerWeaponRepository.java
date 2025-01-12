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

    public PlayerWeaponEntity create(PlayerWeaponEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, PlayerWeaponEntity entity) {
        super.updateById(id, entity);
    }

    public PlayerWeaponEntity getById(int id) {
        return super.getById(id);
    }

    public List<PlayerWeaponEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

    public List<PlayerWeaponEntity> getByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerWeaponEntity where userId = :userId", PlayerWeaponEntity.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }
}
