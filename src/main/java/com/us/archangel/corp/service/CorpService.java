package com.us.archangel.corp.service;

import com.us.archangel.core.GenericContext;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.mapper.CorpMapper;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.repository.CorpRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CorpService {

    private static CorpService instance;

    private final GenericContext<CorpModel> corpContext = new GenericContext<>();

    public static CorpService getInstance() {
        if (instance == null) {
            instance = new CorpService();
        }
        return instance;
    }

    public void create(CorpEntity corpEntity, CorpModel corpModel) {
        // Add to context first
        corpContext.add(corpEntity.getId(), corpModel);
        // Persist to repository as secondary
        CorpRepository.getInstance().create(corpEntity);
    }

    public void update(int id, CorpEntity updatedCorp, CorpModel updatedModel) {
        // Update context first
        corpContext.update(id, updatedModel);
        // Update repository as secondary
        CorpRepository.getInstance().updateById(id, updatedCorp);
    }

    public CorpModel getById(int id) {
        // Check context first
        CorpModel storedVal = corpContext.get(id);
        if (storedVal != null) {
            return storedVal;
        }

        // Fallback to repository if not in context
        CorpEntity entity = CorpRepository.getInstance().getById(id);
        if (entity == null) {
            return null; // Handle case when entity doesn't exist
        }

        // Map entity to model and add to context
        CorpModel model = CorpMapper.toModel(entity);
        corpContext.add(entity.getId(), model);
        return model;
    }

    public List<CorpModel> getAll() {
        // Fetch all models from context
        Map<Integer, CorpModel> models = corpContext.getAll();
        if (!models.isEmpty()) {
            return models.values().stream().collect(Collectors.toList());
        }

        // Fallback to repository if context is empty
        List<CorpEntity> entities = CorpRepository.getInstance().getAll();
        List<CorpModel> modelList = entities.stream()
                .map(CorpMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        modelList.forEach(model -> corpContext.add(model.getId(), model));
        return modelList;
    }

    public List<CorpModel> findManyByDisplayName(String displayName) {
        // Fetch from context and filter by displayName manually
        List<CorpModel> models = corpContext.getAll().values().stream()
                .filter(model -> model.getDisplayName().equalsIgnoreCase(displayName))
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        // Fallback to repository if not found in context
        List<CorpEntity> entities = CorpRepository.getInstance().findManyByDisplayName(displayName);
        models = entities.stream()
                .map(CorpMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        models.forEach(model -> corpContext.add(model.getId(), model));
        return models;
    }

    public List<CorpModel> findManyByIndustry(CorpIndustry industry) {
        // Fetch from context and filter by industry manually
        List<CorpModel> models = corpContext.getAll().values().stream()
                .filter(model -> model.getIndustry() == industry)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        // Fallback to repository if not found in context
        List<CorpEntity> entities = CorpRepository.getInstance().findManyByIndustry(industry);
        models = entities.stream()
                .map(CorpMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        models.forEach(model -> corpContext.add(model.getId(), model));
        return models;
    }

    public void deleteById(int id) {
        // Remove from context first
        corpContext.delete(id);
        // Then delete from repository as secondary
        CorpRepository.getInstance().deleteById(id);
    }
}
