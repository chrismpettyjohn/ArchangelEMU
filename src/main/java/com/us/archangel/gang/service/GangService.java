package com.us.archangel.gang.service;

import com.us.archangel.gang.context.GangContext;
import com.us.archangel.gang.entity.GangEntity;
import com.us.archangel.gang.mapper.GangMapper;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.repository.GangRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class GangService extends GenericService<GangModel, GangContext, GangRepository> {

    private static GangService instance;

    public static synchronized GangService getInstance() {
        if (instance == null) {
            instance = new GangService();
        }
        return instance;
    }

    private GangService() {
        super(GangContext.getInstance(), GangRepository.getInstance(), GangMapper.class, GangEntity.class);
    }

    public void create(GangModel model) {
        super.create(model);
    }

    public void update(int id, GangModel model) {
        super.update(id, model);
    }

    public List<GangModel> getAll() {
        return super.getAll();
    }

    public GangModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
