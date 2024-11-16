package com.us.archangel.corp.service;

import com.us.archangel.corp.context.CorpRoleContext;
import com.us.archangel.corp.entity.CorpRoleEntity;
import com.us.archangel.corp.mapper.CorpRoleMapper;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.repository.CorpRoleRepository;
import com.us.nova.core.GenericService;

import java.util.List;
import java.util.stream.Collectors;

public class CorpRoleService extends GenericService<CorpRoleModel, CorpRoleContext, CorpRoleRepository> {

    private static CorpRoleService instance;

    public static synchronized CorpRoleService getInstance() {
        if (instance == null) {
            instance = new CorpRoleService();
        }
        return instance;
    }

    private CorpRoleService() {
        super(CorpRoleContext.getInstance(), CorpRoleRepository.getInstance(), CorpRoleMapper.class, CorpRoleEntity.class);
    }

    public CorpRoleModel getByCorpAndOrderId(int corpId, int orderId) {
        CorpRoleModel role = context.getAll().values().stream()
                .filter(model -> model.getCorpId() == corpId && model.getOrderId() == orderId)
                .findFirst()
                .orElse(null);

        if (role != null) {
            return role;
        }

        CorpRoleEntity entity = repository.getByCorpAndOrderId(corpId, orderId);
        if (entity == null) {
            return null;
        }

        role = CorpRoleMapper.toModel(entity);
        context.add(entity.getId(), role);
        return role;
    }

    public List<CorpRoleModel> findManyByCorpId(int corpId) {
        List<CorpRoleModel> models = context.getAll().values().stream()
                .filter(model -> model.getCorpId() == corpId)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        List<CorpRoleEntity> entities = repository.findManyByCorpId(corpId);
        models = entities.stream()
                .map(CorpRoleMapper::toModel)
                .collect(Collectors.toList());

        models.forEach(model -> context.add(model.getId(), model));
        return models;
    }
}
