package com.us.archangel.corp.service;

import com.us.archangel.corp.context.CorpInviteContext;
import com.us.archangel.corp.entity.CorpInviteEntity;
import com.us.archangel.corp.mapper.CorpInviteMapper;
import com.us.archangel.corp.model.CorpInviteModel;
import com.us.archangel.corp.repository.CorpInviteRepository;
import com.us.archangel.crime.service.CrimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CorpInviteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorpInviteService.class);

    private static CorpInviteService instance;

    public static CorpInviteService getInstance() {
        if (instance == null) {
            instance = new CorpInviteService();
        }
        return instance;
    }

    private CorpInviteService() {
        LOGGER.info("Corp Invite Service > starting");
        this.getAll();
        LOGGER.info("Corp Invite Service > loaded {} corp invites", this.getAll().size());
    }

    public CorpInviteModel create(CorpInviteEntity corpEntity) {
        CorpInviteRepository.getInstance().create(corpEntity);
        CorpInviteModel model = CorpInviteMapper.toModel(corpEntity);
        CorpInviteContext.getInstance().add(corpEntity.getId(), model);
        return model;
    }

    public void update(CorpInviteEntity updatedCorpInvite, CorpInviteModel updatedModel) {
        CorpInviteRepository.getInstance().updateById(updatedCorpInvite.getId(), updatedCorpInvite);
        CorpInviteContext.getInstance().update(updatedCorpInvite.getId(), updatedModel);
    }

    public CorpInviteModel getById(int id) {
        CorpInviteModel storedVal = CorpInviteContext.getInstance().get(id);
        if (storedVal != null) {
            return storedVal;
        }

        CorpInviteEntity entity = CorpInviteRepository.getInstance().getById(id);
        CorpInviteModel model = CorpInviteMapper.toModel(entity);
        CorpInviteContext.getInstance().add(entity.getId(), model);
        return model;
    }

    public CorpInviteModel getByCorpAndUserId(int corpId, int userId) {
        for (CorpInviteModel model : this.getAll()) {
            if (model.getCorpId() == corpId && model.getUserId() == userId) {
                return model;
            }
        }

        CorpInviteEntity entity = CorpInviteRepository.getInstance().getByCorpAndUserId(corpId, userId);
        CorpInviteModel model = CorpInviteMapper.toModel(entity);
        CorpInviteContext.getInstance().add(entity.getId(), model);
        return model;
    }

    public List<CorpInviteModel> getAll() {
        List<CorpInviteEntity> corpRoleEntities = CorpInviteRepository.getInstance().getAll();
        return corpRoleEntities.stream()
                .map(entity -> CorpInviteContext.getInstance().get(entity.getId()))
                .collect(Collectors.toList());
    }

    public void deleteById(int id) {
        CorpInviteRepository.getInstance().deleteById(id);
        CorpInviteContext.getInstance().delete(id);
    }
}
