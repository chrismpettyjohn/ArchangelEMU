package com.us.archangel.gang.service;

import com.us.archangel.gang.context.GangRoleContext;
import com.us.archangel.gang.entity.GangRoleEntity;
import com.us.archangel.gang.mapper.GangRoleMapper;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.repository.GangRoleRepository;
import com.us.nova.core.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GangRoleService extends GenericService<GangRoleModel, GangRoleContext, GangRoleRepository> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GangRoleService.class);

    private static GangRoleService instance;

    public static synchronized GangRoleService getInstance() {
        if (instance == null) {
            instance = new GangRoleService();
        }
        return instance;
    }

    private GangRoleService() {
        super(GangRoleContext.getInstance(), GangRoleRepository.getInstance(), GangRoleMapper.class);
        LOGGER.info("Gang Role Service > starting");
        this.getAll();  // Preload all gang roles
        LOGGER.info("Gang Role Service > loaded {} gang roles", this.getAll().size());
    }

    public GangRoleModel getByGangIdAndOrderId(int gangId, int orderId) {
        GangRoleModel storedVal = context.getByGangIdAndOrderId(gangId, orderId);
        if (storedVal != null) {
            return storedVal;
        }

        GangRoleEntity entity = repository.getByGangIdAndOrderId(gangId, orderId);
        if (entity != null) {
            GangRoleModel model = GangRoleMapper.toModel(entity);
            context.add(entity.getId(), model);
            return model;
        }
        return null;
    }

    public List<GangRoleModel> getByGangId(int gangId) {
        List<GangRoleModel> storedValues = context.getAll().values().stream()
                .filter(model -> model.getGangId() == gangId)
                .collect(Collectors.toList());

        if (!storedValues.isEmpty()) {
            return storedValues;
        }

        List<GangRoleEntity> entities = repository.getByGangId(gangId);
        List<GangRoleModel> models = entities.stream()
                .map(GangRoleMapper::toModel)
                .collect(Collectors.toList());

        models.forEach(model -> context.add(model.getId(), model));
        return models;
    }
}
