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

    private static com.us.archangel.player.service.PlayerService instance;

    private final GenericContext<PlayerModel> playerContext = new GenericContext<>();

    public static com.us.archangel.player.service.PlayerService getInstance() {
        if (instance == null) {
            instance = new com.us.archangel.player.service.PlayerService();
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
            return null; // Handle case when entity doesn't exist
        }

        // Map entity to model and add to context
        PlayerModel model = PlayerMapper.toModel(entity);
        playerContext.add(entity.getId(), model);
        return model;
    }

    public List<PlayerModel> getAll() {
        // Fetch all models from context
        Map<Integer, PlayerModel> models = playerContext.getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        // Fallback to repository if context is empty
        List<PlayerEntity> entities = PlayerRepository.getInstance().getAll();
        List<PlayerModel> modelList = entities.stream()
                .map(PlayerMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        modelList.forEach(model -> playerContext.add(model.getId(), model));
        return modelList;
    }

    public List<PlayerModel> getByCorpId(int corpId) {
        // Check if there are any players in the context with the specified corpRoleID
        Map<Integer, PlayerModel> allPlayers = playerContext.getAll();
        List<PlayerModel> models = allPlayers.values().stream()
                .filter(player -> player.getCorpId() == corpId)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        // Fallback to repository if context is empty or no match found
        List<PlayerEntity> entities = PlayerRepository.getInstance().getByCorpId(corpId);
        List<PlayerModel> modelList = entities.stream()
                .map(PlayerMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        modelList.forEach(model -> playerContext.add(model.getId(), model));
        return modelList;
    }

    public List<PlayerModel> getByGangId(int gangId) {
        // Check if there are any players in the context with the specified corpRoleID
        Map<Integer, PlayerModel> allPlayers = playerContext.getAll();
        List<PlayerModel> models = allPlayers.values().stream()
                .filter(player -> player.getGangId() == gangId)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        // Fallback to repository if context is empty or no match found
        List<PlayerEntity> entities = PlayerRepository.getInstance().getByCorpId(gangId);
        List<PlayerModel> modelList = entities.stream()
                .map(PlayerMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        modelList.forEach(model -> playerContext.add(model.getId(), model));
        return modelList;
    }

    public List<PlayerModel> getByCorpRoleID(int corpRoleID) {
        // Check if there are any players in the context with the specified corpRoleID
        Map<Integer, PlayerModel> allPlayers = playerContext.getAll();
        List<PlayerModel> models = allPlayers.values().stream()
                .filter(player -> player.getCorpRoleId() == corpRoleID)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models;
        }

        // Fallback to repository if context is empty or no match found
        List<PlayerEntity> entities = PlayerRepository.getInstance().getByCorpRoleId(corpRoleID);
        List<PlayerModel> modelList = entities.stream()
                .map(PlayerMapper::toModel)
                .collect(Collectors.toList());

        // Add to context
        modelList.forEach(model -> playerContext.add(model.getId(), model));
        return modelList;
    }


    public void deleteById(int id) {
        // Remove from context first
        playerContext.delete(id);
        // Then delete from repository as secondary
        PlayerRepository.getInstance().deleteById(id);
    }
}
