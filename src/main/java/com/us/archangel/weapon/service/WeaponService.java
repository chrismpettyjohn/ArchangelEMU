package com.us.archangel.weapon.service;

import com.us.archangel.weapon.context.WeaponContext;
import com.us.archangel.weapon.mapper.WeaponMapper;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.repository.WeaponRepository;
import com.us.nova.core.GenericService;


public class WeaponService extends GenericService<WeaponModel, WeaponContext, WeaponRepository> {

    private static WeaponService instance;

    public static WeaponService getInstance() {
        if (instance == null) {
            instance = new WeaponService();
        }
        return instance;
    }

    private WeaponService() {
        super(WeaponContext.getInstance(), WeaponRepository.getInstance(), WeaponMapper.class);
    }

}
