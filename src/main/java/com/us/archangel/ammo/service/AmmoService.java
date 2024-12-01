package com.us.archangel.ammo.service;

import com.us.archangel.ammo.context.AmmoContext;
import com.us.archangel.ammo.entity.AmmoEntity;
import com.us.archangel.ammo.mapper.AmmoMapper;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.repository.AmmoRepository;
import com.us.nova.core.GenericService;

import java.util.List;


public class AmmoService extends GenericService<AmmoModel, AmmoContext, AmmoRepository> {

    private static AmmoService instance;

    public static AmmoService getInstance() {
        if (instance == null) {
            instance = new AmmoService();
        }
        return instance;
    }

    private AmmoService() {
        super(AmmoContext.getInstance(), AmmoRepository.getInstance(), AmmoMapper.class, AmmoEntity.class);
    }

    public AmmoModel create(AmmoModel model) {
        return super.create(model);
    }

    public void update(int id, AmmoModel model) {
        super.update(id, model);
    }

    public List<AmmoModel> getAll() {
        return super.getAll();
    }

    public AmmoModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

}
