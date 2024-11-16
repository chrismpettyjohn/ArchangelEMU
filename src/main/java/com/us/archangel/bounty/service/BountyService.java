package com.us.archangel.bounty.service;

import com.us.archangel.bounty.context.BountyContext;
import com.us.archangel.bounty.entity.BountyEntity;
import com.us.archangel.bounty.mapper.BountyMapper;
import com.us.archangel.bounty.model.BountyModel;
import com.us.archangel.bounty.repository.BountyRepository;
import com.us.nova.core.GenericService;

import java.util.List;
import java.util.stream.Collectors;

public class BountyService extends GenericService<BountyModel, BountyContext, BountyRepository> {

    private static BountyService instance;

    public static BountyService getInstance() {
        if (instance == null) {
            instance = new BountyService();
        }
        return instance;
    }

    private BountyService() {
        super(BountyContext.getInstance(), BountyRepository.getInstance(), BountyMapper.class, BountyEntity.class);
    }

    public BountyModel create(BountyModel model) {
        return super.create(model);
    }

    public void update(int id, BountyModel model) {
        super.update(id, model);
    }

    public List<BountyModel> getAll() {
        return super.getAll();
    }

    public List<BountyModel> findPendingByUserId(int userId) {
        List<BountyModel> models = BountyContext.getInstance().getAll().values().stream()
                .filter(model -> model.getUserId() == userId && model.getClosedAt() == null)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        List<BountyEntity> entities = BountyRepository.getInstance().findPendingByUserId(userId);
        models = entities.stream()
                .map(BountyMapper::toModel)
                .collect(Collectors.toList());

        models.forEach(model -> BountyContext.getInstance().add(model.getId(), model));
        return models;
    }

    public BountyModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }


}
