package com.us.archangel.corp.service;

import com.us.archangel.corp.context.CorpContext;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.mapper.CorpMapper;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.repository.CorpRepository;
import com.us.nova.core.GenericService;

import java.util.List;
import java.util.stream.Collectors;

public class CorpService extends GenericService<CorpModel, CorpContext, CorpRepository> {

    private static CorpService instance;

    public static CorpService getInstance() {
        if (instance == null) {
            instance = new CorpService();
        }
        return instance;
    }

    private CorpService() {
        super(CorpContext.getInstance(), CorpRepository.getInstance(), CorpMapper.class, CorpEntity.class);
    }

    public void create(CorpModel model) {
        super.create(model);
    }

    public void update(int id, CorpModel model) {
        super.update(id, model);
    }

    public List<CorpModel> getAll() {
        return super.getAll();
    }

    public CorpModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
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
}

