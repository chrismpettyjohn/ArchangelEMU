package com.us.archangel.bounty.service;

import com.us.archangel.bounty.context.BountyContext;
import com.us.archangel.bounty.entity.BountyEntity;
import com.us.archangel.bounty.mapper.BountyMapper;
import com.us.archangel.bounty.model.BountyModel;
import com.us.archangel.bounty.repository.BountyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BountyService {

    private static BountyService instance;


    public static BountyService getInstance() {
        if (instance == null) {
            instance = new BountyService();
        }
        return instance;
    }

    public void create(BountyEntity bountyEntity, BountyModel bountyModel) {
        BountyContext.getInstance().add(bountyEntity.getId(), bountyModel);
        BountyRepository.getInstance().create(bountyEntity);
    }

    public void update(int id, BountyEntity updatedBounty) {
        BountyContext.getInstance().update(id, BountyMapper.toModel(updatedBounty));
        BountyRepository.getInstance().updateById(id, updatedBounty);
    }

    public List<BountyModel> getByUserID(int userID) {
        List<BountyModel> storedValues = BountyContext.getInstance().getByUserId(userID);
        if (!storedValues.isEmpty()) {
            return storedValues;
        }

        List<BountyEntity> entities = BountyRepository.getInstance().getByUserId(userID);
        if (entities == null || entities.isEmpty()) {
            return null;
        }

        List<BountyModel> models = entities.stream()
                .map(BountyMapper::toModel)
                .collect(Collectors.toList());

        models.forEach(model -> BountyContext.getInstance().add(model.getId(), model));
        return models;
    }


    public List<BountyModel> getAll() {
        Map<Integer, BountyModel> models = BountyContext.getInstance().getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<BountyEntity> entities = BountyRepository.getInstance().getAll();
        List<BountyModel> modelList = entities.stream()
                .map(BountyMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> BountyContext.getInstance().add(model.getId(), model));
        return modelList;
    }


    public void deleteById(int id) {
        BountyContext.getInstance().delete(id);
        BountyRepository.getInstance().deleteById(id);
    }
}
