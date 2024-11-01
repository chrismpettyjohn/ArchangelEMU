package com.us.archangel.corp.service;

import com.us.archangel.corp.context.CorpRoleContext;
import com.us.archangel.corp.entity.CorpRoleEntity;
import com.us.archangel.corp.mapper.CorpRoleMapper;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.repository.CorpRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CorpRoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorpRoleService.class);

    private static CorpRoleService instance;

    public static CorpRoleService getInstance() {
        if (instance == null) {
            instance = new CorpRoleService();
        }
        return instance;
    }

    private CorpRoleService() {
        LOGGER.info("Corp Role Service > starting");
        this.getAll();
        LOGGER.info("Corp Role Service > loaded {} corp roles", this.getAll().size());
    }

    public CorpRoleModel create(CorpRoleEntity corpEntity) {
        CorpRoleModel model = CorpRoleMapper.toModel(corpEntity);
        CorpRoleContext.getInstance().add(corpEntity.getId(), model);
        CorpRoleRepository.getInstance().create(corpEntity);
        return model;
    }

    public void update(CorpRoleModel corpRoleModel) {
        CorpRoleContext.getInstance().update(corpRoleModel.getId(), corpRoleModel);
        CorpRoleRepository.getInstance().updateById(corpRoleModel.getId(), CorpRoleMapper.toEntity(corpRoleModel));
    }

    public CorpRoleModel getById(int id) {
        CorpRoleModel storedVal = CorpRoleContext.getInstance().get(id);
        if (storedVal != null) {
            return storedVal;
        }

        CorpRoleEntity entity = CorpRoleRepository.getInstance().getById(id);
        if (entity == null) {
            return null;
        }

        CorpRoleModel model = CorpRoleMapper.toModel(entity);
        CorpRoleContext.getInstance().add(entity.getId(), model);
        return model;
    }

    public CorpRoleModel getByCorpAndOrderId(int corpId, int orderId) {
        List<CorpRoleModel> allRoles = CorpRoleContext.getInstance().getAll().values().stream().toList();
        for (CorpRoleModel role : allRoles) {
            if (role.getCorpId() == corpId && role.getOrderId() == orderId) {
                return role;
            }
        }

        CorpRoleEntity entity = CorpRoleRepository.getInstance().getByCorpAndOrderId(corpId, orderId);
        if (entity == null) {
            return null;
        }

        CorpRoleModel model = CorpRoleMapper.toModel(entity);
        CorpRoleContext.getInstance().add(entity.getId(), model);
        return model;
    }

    public List<CorpRoleModel> getAll() {
        Map<Integer, CorpRoleModel> contextData = CorpRoleContext.getInstance().getAll();
        if (!contextData.isEmpty()) {
            return new ArrayList<>(contextData.values());
        }

        List<CorpRoleEntity> corpRoleEntities = CorpRoleRepository.getInstance().getAll();
        List<CorpRoleModel> modelList = corpRoleEntities.stream()
                .map(CorpRoleMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> CorpRoleContext.getInstance().add(model.getId(), model));
        return modelList;
    }

    public List<CorpRoleModel> findManyByCorpId(int corpId) {
        List<CorpRoleModel> models = CorpRoleContext.getInstance().getAll().values().stream()
                .filter(model -> model.getCorpId() == corpId)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        List<CorpRoleEntity> entities = CorpRoleRepository.getInstance().findManyByCorpId(corpId);
        models = entities.stream()
                .map(CorpRoleMapper::toModel)
                .collect(Collectors.toList());

        models.forEach(model -> CorpRoleContext.getInstance().add(model.getId(), model));
        return models;
    }

    public void deleteById(int id) {
        CorpRoleContext.getInstance().delete(id);
        CorpRoleRepository.getInstance().deleteById(id);
    }
}
