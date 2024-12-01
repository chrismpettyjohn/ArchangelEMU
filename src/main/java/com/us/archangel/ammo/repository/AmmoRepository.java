package com.us.archangel.ammo.repository;

import com.us.archangel.ammo.entity.AmmoEntity;
import com.us.nova.core.GenericRepository;

import java.util.List;

public class AmmoRepository extends GenericRepository<AmmoEntity> {

    private static AmmoRepository instance;

    public static AmmoRepository getInstance() {
        if (instance == null) {
            instance = new AmmoRepository();
        }
        return instance;
    }
    private AmmoRepository() {
        super(AmmoEntity.class);
    }

    public AmmoEntity create(AmmoEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, AmmoEntity entity) {
        super.updateById(id, entity);
    }

    public AmmoEntity getById(int id) {
        return super.getById(id);
    }

    public List<AmmoEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

}
