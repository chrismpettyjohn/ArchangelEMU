package com.us.archangel.corp.service;

import com.us.archangel.corp.context.CorpRoleContext;
import com.us.archangel.corp.entity.CorpRoleEntity;
import com.us.archangel.corp.mapper.CorpRoleMapper;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.repository.CorpRoleRepository;

import java.util.List;
import java.util.stream.Collectors;

import com.us.nova.core.GenericService;

public class CorpRoleService extends GenericService<CorpRoleModel, CorpRoleContext, CorpRoleRepository> {

    private static CorpRoleService instance;

    private CorpRoleService() {
        super(CorpRoleContext.getInstance(), CorpRoleRepository.getInstance(), CorpRoleMapper.class);
    }

    public static CorpRoleService getInstance() {
        if (instance == null) {
            instance = new CorpRoleService();
        }
        return instance;
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

}


