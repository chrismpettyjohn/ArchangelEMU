package com.us.archangel.player.service;

import com.us.archangel.player.context.PlayerKillHistoryContext;
import com.us.archangel.player.entity.PlayerKillHistoryEntity;
import com.us.archangel.player.mapper.PlayerKillHistoryMapper;
import com.us.archangel.player.model.PlayerKillHistoryModel;
import com.us.archangel.player.repository.PlayerKillHistoryRepository;
import com.us.nova.core.GenericService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerKillHistoryService extends GenericService<PlayerKillHistoryModel, PlayerKillHistoryContext, PlayerKillHistoryRepository> {

    private static PlayerKillHistoryService instance;

    public static synchronized PlayerKillHistoryService getInstance() {
        if (instance == null) {
            instance = new PlayerKillHistoryService();
            instance.getAll(); // preload cache
        }
        return instance;
    }

    private PlayerKillHistoryService() {
        super(PlayerKillHistoryContext.getInstance(), PlayerKillHistoryRepository.getInstance(), PlayerKillHistoryMapper.class, PlayerKillHistoryEntity.class);
    }

    public PlayerKillHistoryModel create(PlayerKillHistoryModel model) {
        return super.create(model);
    }

    public void update(int id, PlayerKillHistoryModel model) {
        super.update(id, model);
    }

    public List<PlayerKillHistoryModel> getAll() {
        return super.getAll();
    }

    public PlayerKillHistoryModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

    public List<PlayerKillHistoryModel> getByAttackerUserId(int attackerUserId) {
        Map<Integer, PlayerKillHistoryModel> allKillHistories = context.getAll();
        List<PlayerKillHistoryModel> models = allKillHistories.values().stream()
                .filter(kill -> kill.getAttackerUserId() == attackerUserId)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        List<PlayerKillHistoryEntity> entities = repository.getByAttackerUserId(attackerUserId);
        List<PlayerKillHistoryModel> modelList = entities.stream()
                .map(PlayerKillHistoryMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> context.add(model.getId(), model));
        return modelList;
    }

    public List<PlayerKillHistoryModel> getByVictimUserId(int victimUserId) {
        Map<Integer, PlayerKillHistoryModel> allKillHistories = context.getAll();
        List<PlayerKillHistoryModel> models = allKillHistories.values().stream()
                .filter(kill -> kill.getVictimUserId() == victimUserId)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        List<PlayerKillHistoryEntity> entities = repository.getByVictimUserId(victimUserId);
        List<PlayerKillHistoryModel> modelList = entities.stream()
                .map(PlayerKillHistoryMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> context.add(model.getId(), model));
        return modelList;
    }
}
