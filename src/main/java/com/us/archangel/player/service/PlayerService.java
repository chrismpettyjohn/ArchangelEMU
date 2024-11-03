package com.us.archangel.player.service;

import com.us.archangel.player.context.PlayerContext;
import com.us.archangel.player.entity.PlayerEntity;
import com.us.archangel.player.mapper.PlayerMapper;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.repository.PlayerRepository;
import com.us.nova.core.GenericService;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerService extends GenericService<PlayerModel, PlayerContext, PlayerRepository> {

    private static PlayerService instance;

    public static synchronized PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
            instance.getAll(); // Preload all player data
        }
        return instance;
    }

    private PlayerService() {
        super(PlayerContext.getInstance(), PlayerRepository.getInstance(), PlayerMapper.class, PlayerEntity.class);
    }

    public void create(PlayerModel model) {
        super.create(model);
    }

    public void update(int id, PlayerModel model) {
        super.update(id, model);
    }

    public List<PlayerModel> getAll() {
        return super.getAll();
    }

    public PlayerModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

    public PlayerModel getByUserID(int userID) {
        PlayerModel cachedModel = context.getAll().get(userID);
        if (cachedModel != null) {
            return cachedModel;
        }

        PlayerEntity entity = repository.getByUserId(userID);
        if (entity != null) {
            PlayerModel model = PlayerMapper.toModel(entity);
            context.add(entity.getId(), model);
            return model;
        }
        return null;
    }

    public List<PlayerModel> getByCorpId(int corpId) {
        List<PlayerModel> cachedModels = context.getAll().values().stream()
                .filter(player -> player.getCorpId() == corpId)
                .collect(Collectors.toList());

        if (!cachedModels.isEmpty()) {
            return cachedModels;
        }

        List<PlayerEntity> entities = repository.getByCorpId(corpId);
        List<PlayerModel> modelList = entities.stream()
                .map(PlayerMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> context.add(model.getId(), model));
        return modelList;
    }

    public List<PlayerModel> getByCorpRoleID(int corpRoleID) {
        List<PlayerModel> cachedModels = context.getAll().values().stream()
                .filter(player -> player.getCorpRoleId() == corpRoleID)
                .collect(Collectors.toList());

        if (!cachedModels.isEmpty()) {
            return cachedModels;
        }

        List<PlayerEntity> entities = repository.getByCorpRoleId(corpRoleID);
        List<PlayerModel> modelList = entities.stream()
                .map(PlayerMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> context.add(model.getId(), model));
        return modelList;
    }
}
