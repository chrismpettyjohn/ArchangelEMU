package com.us.archangel.player.repository;

import com.us.archangel.ammo.enums.AmmoSize;
import com.us.archangel.player.entity.PlayerAmmoEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class PlayerAmmoRepository extends GenericRepository<PlayerAmmoEntity> {

    private static PlayerAmmoRepository instance;

    public static synchronized PlayerAmmoRepository getInstance() {
        if (instance == null) {
            instance = new PlayerAmmoRepository();
        }
        return instance;
    }

    private PlayerAmmoRepository() {
        super(PlayerAmmoEntity.class);
    }

    public PlayerAmmoEntity create(PlayerAmmoEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, PlayerAmmoEntity entity) {
        super.updateById(id, entity);
    }

    public PlayerAmmoEntity getById(int id) {
        return super.getById(id);
    }

    public List<PlayerAmmoEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

    public List<PlayerAmmoEntity> getByPlayerId(int playerId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerAmmoEntity where userId = :playerId", PlayerAmmoEntity.class)
                    .setParameter("playerId", playerId)
                    .list();
        }
    }

    public List<PlayerAmmoEntity> getByAmmoSize(AmmoSize ammoSize) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PlayerAmmoEntity where 'size' = :ammoSize", PlayerAmmoEntity.class)
                    .setParameter("size", ammoSize)
                    .list();
        }
    }
}
