package com.us.archangel.gang.service;

import com.us.archangel.gang.context.GangInviteContext;
import com.us.archangel.gang.entity.GangInviteEntity;
import com.us.archangel.gang.mapper.GangInviteMapper;
import com.us.archangel.gang.model.GangInviteModel;
import com.us.archangel.gang.repository.GangInviteRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class GangInviteService extends GenericService<GangInviteModel, GangInviteContext, GangInviteRepository> {

    private static GangInviteService instance;

    public static synchronized GangInviteService getInstance() {
        if (instance == null) {
            instance = new GangInviteService();
        }
        return instance;
    }

    private GangInviteService() {
        super(GangInviteContext.getInstance(), GangInviteRepository.getInstance(), GangInviteMapper.class, GangInviteEntity.class);
    }

    public GangInviteModel create(GangInviteModel model) {
        return super.create(model);
    }

    public void update(int id, GangInviteModel model) {
        super.update(id, model);
    }

    public List<GangInviteModel> getAll() {
        return super.getAll();
    }

    public GangInviteModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

    public GangInviteModel getByGangAndUserId(int gangId, int userId) {
        for (GangInviteModel model : this.getAll()) {
            if (model.getGangId() == gangId && model.getUserId() == userId) {
                return model;
            }
        }

        GangInviteEntity entity = repository.getByGangAndUserId(gangId, userId);
        if (entity != null) {
            GangInviteModel model = GangInviteMapper.toModel(entity);
            context.add(entity.getId(), model);
            return model;
        }
        return null;
    }
}
