package com.us.archangel.player.service;

import com.us.archangel.core.GenericContext;
import com.us.archangel.player.entity.PlayerSkillEntity;
import com.us.archangel.player.mapper.PlayerSkillMapper;
import com.us.archangel.player.model.PlayerSkillModel;
import com.us.archangel.player.repository.PlayerSkillRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerSkillService {

    private static PlayerSkillService instance;

    private final GenericContext<PlayerSkillModel> playerSkillContext = new GenericContext<>();

    public static PlayerSkillService getInstance() {
        if (instance == null) {
            instance = new PlayerSkillService();
        }
        return instance;
    }

    public void create(PlayerSkillEntity playerEntity, PlayerSkillModel playerModel) {
        playerSkillContext.add(playerEntity.getId(), playerModel);
        PlayerSkillRepository.getInstance().create(playerEntity);
    }

    public void update(int id, PlayerSkillEntity updatedPlayerSkill) {
        playerSkillContext.update(id, PlayerSkillMapper.toModel(updatedPlayerSkill));
        PlayerSkillRepository.getInstance().updateById(id, updatedPlayerSkill);
    }

    public PlayerSkillModel getByUserID(int userID) {
        PlayerSkillModel storedVal = playerSkillContext.get(userID);
        if (storedVal != null) {
            return storedVal;
        }

        PlayerSkillEntity entity = PlayerSkillRepository.getInstance().getByUserId(userID);
        if (entity == null) {
            return null;
        }

        PlayerSkillModel model = PlayerSkillMapper.toModel(entity);
        playerSkillContext.add(entity.getId(), model);
        return model;
    }

    public List<PlayerSkillModel> getAll() {
        Map<Integer, PlayerSkillModel> models = playerSkillContext.getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<PlayerSkillEntity> entities = PlayerSkillRepository.getInstance().getAll();
        List<PlayerSkillModel> modelList = entities.stream()
                .map(PlayerSkillMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> playerSkillContext.add(model.getId(), model));
        return modelList;
    }

    public void deleteById(int id) {
        playerSkillContext.delete(id);
        PlayerSkillRepository.getInstance().deleteById(id);
    }
}
