package com.us.archangel.gang.service;

import com.us.archangel.core.GenericContext;
import com.us.archangel.gang.entity.GangEntity;
import com.us.archangel.gang.mapper.GangMapper;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.repository.GangRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GangService {

    private static GangService instance;

    private final GenericContext<GangModel> gangContext = new GenericContext<>();

    public static GangService getInstance() {
        if (instance == null) {
            instance = new GangService();
        }
        return instance;
    }

    public void create(GangEntity gangEntity, GangModel gangModel) {
        // Add to context first
        gangContext.add(gangEntity.getId(), gangModel);
        // Persist to repository as secondary
        GangRepository.getInstance().create(gangEntity);
    }

    public void update(int id, GangEntity updatedGang, GangModel updatedModel) {
        // Update context first
        gangContext.update(id, updatedModel);
        // Update repository as secondary
        GangRepository.getInstance().updateById(id, updatedGang);
    }

    public GangModel getById(int id) {
        // Check context first
        GangModel storedVal = gangContext.get(id);
        if (storedVal != null) {
            return storedVal;
        }

        // Fallback to repository if not in context
        GangEntity entity = GangRepository.getInstance().getById(id);
        if (entity == null) {
            return null; // Handle case when entity doesn't exist
        }

        // Map entity to model and add to context
        GangModel model = GangMapper.toModel(entity);
        gangContext.add(entity.getId(), model);
        return model;
    }

    public List<GangModel> getAll() {
        // Fetch all models from context
        Map<Integer, GangModel> models = gangContext.getAll();
        if (!models.isEmpty()) {
            return models.values().stream().collect(Collectors.toList());
        }

        // Fallback to repository if context is empty
        List<GangEntity> entities = GangRepository.getInstance().getAll();
        List<GangModel> modelList = entities.stream()
                .map(GangMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        modelList.forEach(model -> gangContext.add(model.getId(), model));
        return modelList;
    }

    public List<GangModel> findManyByDisplayName(String displayName) {
        // Fetch from context and filter by displayName manually
        List<GangModel> models = gangContext.getAll().values().stream()
                .filter(model -> model.getDisplayName().equalsIgnoreCase(displayName))
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        // Fallback to repository if not found in context
        List<GangEntity> entities = GangRepository.getInstance().findManyByDisplayName(displayName);
        models = entities.stream()
                .map(GangMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        models.forEach(model -> gangContext.add(model.getId(), model));
        return models;
    }
    public void deleteById(int id) {
        // Remove from context first
        gangContext.delete(id);
        // Then delete from repository as secondary
        GangRepository.getInstance().deleteById(id);
    }
}
