package com.us.archangel.player.service;

import com.us.archangel.core.GenericContext;
import com.us.archangel.player.entity.PlayerBankAccountEntity;
import com.us.archangel.player.mapper.PlayerBankAccountMapper;
import com.us.archangel.player.model.PlayerBankAccountModel;
import com.us.archangel.player.repository.PlayerBankAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerBankAccountService {

    private static PlayerBankAccountService instance;

    private final GenericContext<PlayerBankAccountModel> playerBankAccountContext = new GenericContext<>();

    public static PlayerBankAccountService getInstance() {
        if (instance == null) {
            instance = new PlayerBankAccountService();
        }
        return instance;
    }

    public void create(PlayerBankAccountEntity playerEntity) {
        playerBankAccountContext.add(playerEntity.getId(), PlayerBankAccountMapper.toModel(playerEntity));
        PlayerBankAccountRepository.getInstance().create(playerEntity);
    }

    public void update(int id, PlayerBankAccountEntity updatedPlayerBankAccount) {
        playerBankAccountContext.update(id, PlayerBankAccountMapper.toModel(updatedPlayerBankAccount));
        PlayerBankAccountRepository.getInstance().updateById(id, updatedPlayerBankAccount);
    }

    public PlayerBankAccountModel getByUserIdAndCorpId(int userId, int corpId) {
        // Check in-memory context first
        List<PlayerBankAccountModel> models = playerBankAccountContext.getAll().values().stream()
                .filter(model -> model.getUserId() == userId && model.getCorpId() == corpId)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models.get(0); // Assuming one match based on userId and corpId
        }

        // If not in context, check in repository
        PlayerBankAccountEntity entity = PlayerBankAccountRepository.getInstance().getByUserIdAndCorpId(userId, corpId);
        if (entity != null) {
            PlayerBankAccountModel model = PlayerBankAccountMapper.toModel(entity);
            playerBankAccountContext.add(entity.getId(), model); // Cache it in context
            return model;
        }
        return null; // Or throw an exception if appropriate
    }

    public List<PlayerBankAccountModel> getAll() {
        Map<Integer, PlayerBankAccountModel> models = playerBankAccountContext.getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<PlayerBankAccountEntity> entities = PlayerBankAccountRepository.getInstance().getAll();
        List<PlayerBankAccountModel> modelList = entities.stream()
                .map(PlayerBankAccountMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> playerBankAccountContext.add(model.getId(), model));
        return modelList;
    }

    public void deleteById(int id) {
        playerBankAccountContext.delete(id);
        PlayerBankAccountRepository.getInstance().deleteById(id);
    }
}
