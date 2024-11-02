package com.us.archangel.gang.service;

import com.us.archangel.gang.context.GangInviteContext;
import com.us.archangel.gang.entity.GangInviteEntity;
import com.us.archangel.gang.mapper.GangInviteMapper;
import com.us.archangel.gang.model.GangInviteModel;
import com.us.archangel.gang.repository.GangInviteRepository;
import com.us.nova.core.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class GangInviteService extends GenericService<GangInviteModel, GangInviteContext, GangInviteRepository> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GangInviteService.class);

    private static GangInviteService instance;

    public static synchronized GangInviteService getInstance() {
        if (instance == null) {
            instance = new GangInviteService();
        }
        return instance;
    }

    private GangInviteService() {
        super(GangInviteContext.getInstance(), GangInviteRepository.getInstance(), GangInviteMapper.class);
        LOGGER.info("Gang Invite Service > starting");
        this.getAll();  // preload all gang invites
        LOGGER.info("Gang Invite Service > loaded {} gang invites", this.getAll().size());
    }

    public GangInviteModel create(GangInviteEntity gangEntity) {
        repository.create(gangEntity);
        GangInviteModel model = GangInviteMapper.toModel(gangEntity);
        context.add(gangEntity.getId(), model);
        return model;
    }

    public void update(GangInviteEntity updatedGangInvite, GangInviteModel updatedModel) {
        repository.updateById(updatedGangInvite.getId(), updatedGangInvite);
        context.update(updatedGangInvite.getId(), updatedModel);
    }

    public GangInviteModel getById(int id) {
        GangInviteModel storedVal = context.get(id);
        if (storedVal != null) {
            return storedVal;
        }

        GangInviteEntity entity = repository.getById(id);
        if (entity != null) {
            GangInviteModel model = GangInviteMapper.toModel(entity);
            context.add(entity.getId(), model);
            return model;
        }
        return null;
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

    @Override
    public List<GangInviteModel> getAll() {
        List<GangInviteEntity> entities = repository.getAll();
        return entities.stream()
                .map(entity -> {
                    GangInviteModel model = context.get(entity.getId());
                    if (model == null) {
                        model = GangInviteMapper.toModel(entity);
                        context.add(entity.getId(), model);
                    }
                    return model;
                })
                .collect(Collectors.toList());
    }

    public void deleteById(int id) {
        repository.deleteById(id);
        context.delete(id);
    }
}
