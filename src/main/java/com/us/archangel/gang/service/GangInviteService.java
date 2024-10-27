package com.us.archangel.gang.service;

import com.us.archangel.gang.context.GangInviteContext;
import com.us.archangel.gang.entity.GangInviteEntity;
import com.us.archangel.gang.mapper.GangInviteMapper;
import com.us.archangel.gang.model.GangInviteModel;
import com.us.archangel.gang.repository.GangInviteRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GangInviteService {

    private static GangInviteService instance;

    public static GangInviteService getInstance() {
        if (instance == null) {
            instance = new GangInviteService();
        }
        return instance;
    }

    public GangInviteModel create(GangInviteEntity gangEntity) {
        GangInviteRepository.getInstance().create(gangEntity);
        GangInviteModel model = GangInviteMapper.toModel(gangEntity);
        GangInviteContext.getInstance().add(gangEntity.getId(), model);
        return model;
    }

    public void update(GangInviteEntity updatedGangInvite, GangInviteModel updatedModel) {
        GangInviteRepository.getInstance().updateById(updatedGangInvite.getId(), updatedGangInvite);
        GangInviteContext.getInstance().update(updatedGangInvite.getId(), updatedModel);
    }

    public GangInviteModel getById(int id) {
        GangInviteModel storedVal = GangInviteContext.getInstance().get(id);
        if (storedVal != null) {
            return storedVal;
        }

        GangInviteEntity entity = GangInviteRepository.getInstance().getById(id);
        GangInviteModel model = GangInviteMapper.toModel(entity);
        GangInviteContext.getInstance().add(entity.getId(), model);
        return model;
    }

    public GangInviteModel getByGangAndUserId(int gangId, int userId) {
        for (GangInviteModel model : this.getAll()) {
            if (model.getGangId() == gangId && model.getUserId() == userId) {
                return model;
            }
        }

        GangInviteEntity entity = GangInviteRepository.getInstance().getByGangAndUserId(gangId, userId);
        GangInviteModel model = GangInviteMapper.toModel(entity);
        GangInviteContext.getInstance().add(entity.getId(), model);
        return model;
    }

    public List<GangInviteModel> getAll() {
        List<GangInviteEntity> gangRoleEntities = GangInviteRepository.getInstance().getAll();
        return gangRoleEntities.stream()
                .map(entity -> GangInviteContext.getInstance().get(entity.getId()))
                .collect(Collectors.toList());
    }

    public void deleteById(int id) {
        GangInviteRepository.getInstance().deleteById(id);
        GangInviteContext.getInstance().delete(id);
    }
}
