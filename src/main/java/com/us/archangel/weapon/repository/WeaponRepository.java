package com.us.archangel.weapon.repository;

import com.us.archangel.weapon.entity.WeaponEntity;
import com.us.nova.core.GenericRepository;

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

}
