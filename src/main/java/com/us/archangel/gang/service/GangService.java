package com.us.archangel.gang.service;

import com.us.archangel.gang.context.GangContext;
import com.us.archangel.gang.entity.GangEntity;
import com.us.archangel.gang.mapper.GangMapper;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.repository.GangRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GangService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GangService.class);

    private static GangService instance;

    public static GangService getInstance() {
        if (instance == null) {
            instance = new GangService();
        }
        return instance;
    }

    private GangService() {
        LOGGER.info("Gang Service > starting");
        this.getAll();
        LOGGER.info("Gang Service > loaded {} gangs", this.getAll().size());
    }

    public void create(GangEntity gangEntity, GangModel gangModel) {
        GangContext.getInstance().add(gangEntity.getId(), gangModel);
        GangRepository.getInstance().create(gangEntity);
    }

    public void update(int id, GangEntity updatedGang, GangModel updatedModel) {
        GangContext.getInstance().update(id, updatedModel);
        GangRepository.getInstance().updateById(id, updatedGang);
    }

    public GangModel getById(int id) {
        GangModel storedVal = GangContext.getInstance().get(id);
        if (storedVal != null) {
            return storedVal;
        }

        GangEntity entity = GangRepository.getInstance().getById(id);
        if (entity == null) {
            return null;
        }

        GangModel model = GangMapper.toModel(entity);
        GangContext.getInstance().add(entity.getId(), model);
        return model;
    }

    public List<GangModel> getAll() {
        Map<Integer, GangModel> models = GangContext.getInstance().getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<GangEntity> entities = GangRepository.getInstance().getAll();
        List<GangModel> modelList = entities.stream()
                .map(GangMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> GangContext.getInstance().add(model.getId(), model));
        return modelList;
    }

    public void deleteById(int id) {
        GangContext.getInstance().delete(id);
        GangRepository.getInstance().deleteById(id);
    }
}
