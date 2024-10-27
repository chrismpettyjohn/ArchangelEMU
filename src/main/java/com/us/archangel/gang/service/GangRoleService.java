package com.us.archangel.gang.service;

import com.us.archangel.gang.context.GangRoleContext;
import com.us.archangel.gang.entity.GangRoleEntity;
import com.us.archangel.gang.mapper.GangRoleMapper;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.repository.GangRoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GangRoleService {

    private static GangRoleService instance;

    public static GangRoleService getInstance() {
        if (instance == null) {
            instance = new GangRoleService();
        }
        return instance;
    }

    public GangRoleModel create(GangRoleEntity gangEntity) {
        GangRoleModel model = GangRoleMapper.toModel(gangEntity);
        GangRoleContext.getInstance().add(gangEntity.getId(), model);
        GangRoleRepository.getInstance().create(gangEntity);
        return model;
    }

    public void update(GangRoleModel gangRoleModel) {
        GangRoleContext.getInstance().update(gangRoleModel.getId(), gangRoleModel);
        GangRoleRepository.getInstance().updateById(gangRoleModel.getId(), GangRoleMapper.toEntity(gangRoleModel));
    }

    public GangRoleModel getById(int id) {
        GangRoleModel storedVal = GangRoleContext.getInstance().get(id);
        if (storedVal != null) {
            return storedVal;
        }

        GangRoleEntity entity = GangRoleRepository.getInstance().getById(id);
        if (entity == null) {
            return null;
        }

        GangRoleModel model = GangRoleMapper.toModel(entity);
        GangRoleContext.getInstance().add(entity.getId(), model);
        return model;
    }

    public GangRoleModel getByGangIdAndOrderId(int gangId, int orderId) {
        GangRoleModel storedVal = GangRoleContext.getInstance().getByGangIdAndOrderId(gangId, orderId);
        if (storedVal != null) {
            return storedVal;
        }

        GangRoleEntity entity = GangRoleRepository.getInstance().getByGangIdAndOrderId(gangId, orderId);
        if (entity == null) {
            return null;
        }

        GangRoleModel model = GangRoleMapper.toModel(entity);
        GangRoleContext.getInstance().add(entity.getId(), model);
        return model;
    }

    public List<GangRoleModel> getByGangId(int gangId) {
        List<GangRoleModel> storedValues = GangRoleContext.getInstance().getAll().values().stream()
                .filter(model -> model.getGangId() == gangId)
                .collect(Collectors.toList());

        if (!storedValues.isEmpty()) {
            return storedValues;
        }

        List<GangRoleEntity> entities = GangRoleRepository.getInstance().getByGangId(gangId);
        List<GangRoleModel> models = entities.stream()
                .map(GangRoleMapper::toModel)
                .collect(Collectors.toList());

        models.forEach(model -> GangRoleContext.getInstance().add(model.getId(), model));
        return models;
    }


    public List<GangRoleModel> getAll() {
        Map<Integer, GangRoleModel> contextData = GangRoleContext.getInstance().getAll();
        if (!contextData.isEmpty()) {
            return new ArrayList<>(contextData.values());
        }

        List<GangRoleEntity> gangRoleEntities = GangRoleRepository.getInstance().getAll();
        List<GangRoleModel> modelList = gangRoleEntities.stream()
                .map(GangRoleMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> GangRoleContext.getInstance().add(model.getId(), model));
        return modelList;
    }

    public void deleteById(int id) {
        GangRoleContext.getInstance().delete(id);
        GangRoleRepository.getInstance().deleteById(id);
    }
}
