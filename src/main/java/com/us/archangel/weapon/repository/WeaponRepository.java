package com.us.archangel.weapon.repository;

import com.us.archangel.weapon.entity.WeaponEntity;
import com.us.nova.core.GenericRepository;

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

}
