package com.us.archangel.weapon.service;

import com.us.archangel.weapon.context.WeaponContext;
import com.us.archangel.weapon.entity.WeaponEntity;
import com.us.archangel.weapon.mapper.WeaponMapper;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.repository.WeaponRepository;
import com.us.nova.core.GenericService;

import java.util.List;


public class WeaponService extends GenericService<WeaponModel, WeaponContext, WeaponRepository> {

    private static WeaponService instance;

    public static WeaponService getInstance() {
        if (instance == null) {
            instance = new WeaponService();
        }
        return instance;
    }

    private WeaponService() {
        super(WeaponContext.getInstance(), WeaponRepository.getInstance(), WeaponMapper.class, WeaponEntity.class);
    }

    public WeaponModel create(WeaponModel model) {
        return super.create(model);
    }

    public void update(int id, WeaponModel model) {
        super.update(id, model);
    }

    public List<WeaponModel> getAll() {
        return super.getAll();
    }

    public WeaponModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

}
