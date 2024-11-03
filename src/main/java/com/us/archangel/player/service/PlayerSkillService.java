package com.us.archangel.player.service;

import com.us.archangel.player.context.PlayerSkillContext;
import com.us.archangel.player.entity.PlayerSkillEntity;
import com.us.archangel.player.mapper.PlayerSkillMapper;
import com.us.archangel.player.model.PlayerSkillModel;
import com.us.archangel.player.repository.PlayerSkillRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class PlayerSkillService extends GenericService<PlayerSkillModel, PlayerSkillContext, PlayerSkillRepository> {

    private static PlayerSkillService instance;

    public static synchronized PlayerSkillService getInstance() {
        if (instance == null) {
            instance = new PlayerSkillService();
            instance.getAll(); // preload cache
        }
        return instance;
    }

    private PlayerSkillService() {
        super(PlayerSkillContext.getInstance(), PlayerSkillRepository.getInstance(), PlayerSkillMapper.class, PlayerSkillEntity.class);
    }

    public void create(PlayerSkillModel model) {
        super.create(model);
    }

    public void update(int id, PlayerSkillModel model) {
        super.update(id, model);
    }

    public List<PlayerSkillModel> getAll() {
        return super.getAll();
    }

    public PlayerSkillModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

    public PlayerSkillModel getByUserID(int userID) {
        PlayerSkillModel cachedModel = context.get(userID);
        if (cachedModel != null) {
            return cachedModel;
        }

        PlayerSkillEntity entity = repository.getByUserId(userID);
        if (entity != null) {
            PlayerSkillModel model = PlayerSkillMapper.toModel(entity);
            context.add(entity.getId(), model);
            return model;
        }
        return null;
    }
}
