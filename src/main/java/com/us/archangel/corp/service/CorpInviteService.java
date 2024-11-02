package com.us.archangel.corp.service;

import com.us.archangel.corp.context.CorpInviteContext;
import com.us.archangel.corp.entity.CorpInviteEntity;
import com.us.archangel.corp.mapper.CorpInviteMapper;
import com.us.archangel.corp.model.CorpInviteModel;
import com.us.archangel.corp.repository.CorpInviteRepository;
import com.us.nova.core.GenericService;

import java.util.List;
import java.util.stream.Collectors;

public class CorpInviteService extends GenericService<CorpInviteModel, CorpInviteContext, CorpInviteRepository> {
    private static CorpInviteService instance;

    private CorpInviteService() {
        super(CorpInviteContext.getInstance(), CorpInviteRepository.getInstance(), CorpInviteMapper.class);
    }

    public static synchronized CorpInviteService getInstance() {
        if (instance == null) {
            instance = new CorpInviteService();
        }
        return instance;
    }

    public CorpInviteModel create(CorpInviteEntity corpEntity) {
        repository.create(corpEntity);
        CorpInviteModel model = CorpInviteMapper.toModel(corpEntity);
        context.add(corpEntity.getId(), model);
        return model;
    }

    public void update(CorpInviteEntity updatedCorpInvite, CorpInviteModel updatedModel) {
        repository.updateById(updatedCorpInvite.getId(), updatedCorpInvite);
        context.update(updatedCorpInvite.getId(), updatedModel);
    }

    public CorpInviteModel getById(int id) {
        CorpInviteModel storedVal = context.get(id);
        if (storedVal != null) {
            return storedVal;
        }

        CorpInviteEntity entity = repository.getById(id);
        if (entity != null) {
            CorpInviteModel model = CorpInviteMapper.toModel(entity);
            context.add(entity.getId(), model);
            return model;
        }
        return null;
    }

    public CorpInviteModel getByCorpAndUserId(int corpId, int userId) {
        for (CorpInviteModel model : this.getAll()) {
            if (model.getCorpId() == corpId && model.getUserId() == userId) {
                return model;
            }
        }

        CorpInviteEntity entity = repository.getByCorpAndUserId(corpId, userId);
        if (entity != null) {
            CorpInviteModel model = CorpInviteMapper.toModel(entity);
            context.add(entity.getId(), model);
            return model;
        }
        return null;
    }

    @Override
    public List<CorpInviteModel> getAll() {
        List<CorpInviteEntity> entities = repository.getAll();
        return entities.stream()
                .map(entity -> {
                    CorpInviteModel model = context.get(entity.getId());
                    if (model == null) {
                        model = CorpInviteMapper.toModel(entity);
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
