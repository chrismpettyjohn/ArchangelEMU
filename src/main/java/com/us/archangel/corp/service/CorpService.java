package com.us.archangel.corp.service;

import com.us.archangel.corp.context.CorpContext;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.mapper.CorpMapper;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.repository.CorpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CorpService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorpService.class);

    private static CorpService instance;

    public static CorpService getInstance() {
        if (instance == null) {
            instance = new CorpService();
        }
        return instance;
    }

    private CorpService() {
        LOGGER.info("Corp Service > starting");
        this.getAll();
        LOGGER.info("Corp Service > loaded {} corps", this.getAll().size());
    }

    public void create(CorpEntity corpEntity, CorpModel corpModel) {
        CorpContext.getInstance().add(corpEntity.getId(), corpModel);
        CorpRepository.getInstance().create(corpEntity);
    }

    public void update(int id, CorpEntity updatedCorp, CorpModel updatedModel) {
        CorpContext.getInstance().update(id, updatedModel);
        CorpRepository.getInstance().updateById(id, updatedCorp);
    }

    public CorpModel getById(int id) {
        CorpModel storedVal = CorpContext.getInstance().get(id);
        if (storedVal != null) {
            return storedVal;
        }

        CorpEntity entity = CorpRepository.getInstance().getById(id);
        if (entity == null) {
            return null;
        }

        CorpModel model = CorpMapper.toModel(entity);
        CorpContext.getInstance().add(entity.getId(), model);
        return model;
    }

    public List<CorpModel> getAll() {
        Map<Integer, CorpModel> models = CorpContext.getInstance().getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<CorpEntity> entities = CorpRepository.getInstance().getAll();
        List<CorpModel> modelList = entities.stream()
                .map(CorpMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> CorpContext.getInstance().add(model.getId(), model));
        return modelList;
    }

    public List<CorpModel> findManyByIndustry(CorpIndustry industry) {
        List<CorpModel> models = CorpContext.getInstance().getAll().values().stream()
                .filter(model -> model.getIndustry() == industry)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        List<CorpEntity> entities = CorpRepository.getInstance().findManyByIndustry(industry);
        models = entities.stream()
                .map(CorpMapper::toModel)
                .collect(Collectors.toList());

        models.forEach(model -> CorpContext.getInstance().add(model.getId(), model));
        return models;
    }

    public void deleteById(int id) {
        CorpContext.getInstance().delete(id);
        CorpRepository.getInstance().deleteById(id);
    }
}
