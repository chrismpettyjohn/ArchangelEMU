package com.us.archangel.weapon.repository;

import com.us.archangel.ammo.enums.AmmoSize;
import com.us.archangel.player.entity.PlayerAmmoEntity;
import com.us.archangel.weapon.entity.WeaponEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

import java.util.List;

public class WeaponRepository extends GenericRepository<WeaponEntity> {

    private static WeaponRepository instance;

    public static WeaponRepository getInstance() {
        if (instance == null) {
            instance = new WeaponRepository();
        }
        return instance;
    }
    private WeaponRepository() {
        super(WeaponEntity.class);
    }

    public WeaponEntity create(WeaponEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, WeaponEntity entity) {
        super.updateById(id, entity);
    }

    public WeaponEntity getById(int id) {
        return super.getById(id);
    }

    public List<WeaponEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

    public WeaponEntity getByUniqueName(String uniqueName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from WeaponEntity where uniqueName = :uniqueName", WeaponEntity.class)
                    .setParameter("uniqueName", uniqueName)
                    .getSingleResultOrNull();
        }
    }

}
