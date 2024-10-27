package com.us.archangel.player.service;

import com.us.archangel.core.GenericContext;
import com.us.archangel.player.entity.PlayerEntity;
import com.us.archangel.player.mapper.PlayerMapper;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerService {

    private static PlayerService instance;

    private final GenericContext<PlayerModel> playerContext = new GenericContext<>();

    public static PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }
        return instance;
    }

    public void create(PlayerEntity playerEntity, PlayerModel playerModel) {
        playerContext.add(playerEntity.getId(), playerModel);
        PlayerRepository.getInstance().create(playerEntity);
    }

    public void update(int id, PlayerEntity updatedPlayer) {
        playerContext.update(id, PlayerMapper.toModel(updatedPlayer));
        PlayerRepository.getInstance().updateById(id, updatedPlayer);
    }

    public PlayerModel getByUserID(int userID) {
        PlayerModel storedVal = playerContext.get(userID);
        if (storedVal != null) {
            return storedVal;
        }

        PlayerEntity entity = PlayerRepository.getInstance().getByUserId(userID);
        if (entity == null) {
            return null;
        }

        PlayerModel model = PlayerMapper.toModel(entity);
        playerContext.add(entity.getId(), model);
        return model;
    }

    public List<PlayerModel> getAll() {
        Map<Integer, PlayerModel> models = playerContext.getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<PlayerEntity> entities = PlayerRepository.getInstance().getAll();
        List<PlayerModel> modelList = entities.stream()
                .map(PlayerMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> playerContext.add(model.getId(), model));
        return modelList;
    }

    public List<PlayerModel> getByCorpId(int corpId) {
        Map<Integer, PlayerModel> allPlayers = playerContext.getAll();
        List<PlayerModel> models = allPlayers.values().stream()
                .filter(player -> player.getCorpId() == corpId)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        List<PlayerEntity> entities = PlayerRepository.getInstance().getByCorpId(corpId);
        List<PlayerModel> modelList = entities.stream()
                .map(PlayerMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        modelList.forEach(model -> playerContext.add(model.getId(), model));
        return modelList;
    }

    public List<PlayerModel> getByCorpRoleID(int corpRoleID) {
        Map<Integer, PlayerModel> allPlayers = playerContext.getAll();
        List<PlayerModel> models = allPlayers.values().stream()
                .filter(player -> player.getCorpRoleId() == corpRoleID)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        List<PlayerEntity> entities = PlayerRepository.getInstance().getByCorpRoleId(corpRoleID);
        List<PlayerModel> modelList = entities.stream()
                .map(PlayerMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> playerContext.add(model.getId(), model));
        return modelList;
    }


    public void deleteById(int id) {
        playerContext.delete(id);
        PlayerRepository.getInstance().deleteById(id);
    }
}
