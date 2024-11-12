package com.us.archangel.corp.service;

import com.us.archangel.corp.context.CorpInviteContext;
import com.us.archangel.corp.entity.CorpInviteEntity;
import com.us.archangel.corp.mapper.CorpInviteMapper;
import com.us.archangel.corp.model.CorpInviteModel;
import com.us.archangel.corp.repository.CorpInviteRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class CorpInviteService extends GenericService<CorpInviteModel, CorpInviteContext, CorpInviteRepository> {
    private static CorpInviteService instance;

    private CorpInviteService() {
        super(CorpInviteContext.getInstance(), CorpInviteRepository.getInstance(), CorpInviteMapper.class, CorpInviteEntity.class);
    }

    public static synchronized CorpInviteService getInstance() {
        if (instance == null) {
            instance = new CorpInviteService();
        }
        return instance;
    }

    public CorpInviteModel create(CorpInviteModel model) {
        return super.create(model);
    }

    public void update(int id, CorpInviteModel model) {
        super.update(id, model);
    }

    public List<CorpInviteModel> getAll() {
        return super.getAll();
    }

    public CorpInviteModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
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
}
