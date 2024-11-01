package com.us.archangel.player.service;

import com.us.archangel.player.context.PlayerBankAccountContext;
import com.us.archangel.player.entity.PlayerBankAccountEntity;
import com.us.archangel.player.mapper.PlayerBankAccountMapper;
import com.us.archangel.player.model.PlayerBankAccountModel;
import com.us.archangel.player.repository.PlayerBankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerBankAccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerBankAccountService.class);

    private static PlayerBankAccountService instance;

    public static PlayerBankAccountService getInstance() {
        if (instance == null) {
            instance = new PlayerBankAccountService();
        }
        return instance;
    }

    private PlayerBankAccountService() {
        LOGGER.info("Player Bank Account Service > starting");
        this.getAll();
        LOGGER.info("Player Bank Account Service > loaded {} bank accounts", this.getAll().size());
    }

    public void create(PlayerBankAccountEntity playerEntity) {
        PlayerBankAccountContext.getInstance().add(playerEntity.getId(), PlayerBankAccountMapper.toModel(playerEntity));
        PlayerBankAccountRepository.getInstance().create(playerEntity);
    }

    public void update(int id, PlayerBankAccountEntity updatedPlayerBankAccount) {
        PlayerBankAccountContext.getInstance().update(id, PlayerBankAccountMapper.toModel(updatedPlayerBankAccount));
        PlayerBankAccountRepository.getInstance().updateById(id, updatedPlayerBankAccount);
    }

    public PlayerBankAccountModel getByUserIdAndCorpId(int userId, int corpId) {
        // Check in-memory context first
        List<PlayerBankAccountModel> models = PlayerBankAccountContext.getInstance().getAll().values().stream()
                .filter(model -> model.getUserId() == userId && model.getCorpId() == corpId)
                .collect(Collectors.toList());

        if (!models.isEmpty()) {
            return models.get(0); // Assuming one match based on userId and corpId
        }

        // If not in context, check in repository
        PlayerBankAccountEntity entity = PlayerBankAccountRepository.getInstance().getByUserIdAndCorpId(userId, corpId);
        if (entity != null) {
            PlayerBankAccountModel model = PlayerBankAccountMapper.toModel(entity);
            PlayerBankAccountContext.getInstance().add(entity.getId(), model); // Cache it in context
            return model;
        }
        return null; // Or throw an exception if appropriate
    }

    public List<PlayerBankAccountModel> getAll() {
        Map<Integer, PlayerBankAccountModel> models = PlayerBankAccountContext.getInstance().getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<PlayerBankAccountEntity> entities = PlayerBankAccountRepository.getInstance().getAll();
        List<PlayerBankAccountModel> modelList = entities.stream()
                .map(PlayerBankAccountMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> PlayerBankAccountContext.getInstance().add(model.getId(), model));
        return modelList;
    }

    public void deleteById(int id) {
        PlayerBankAccountContext.getInstance().delete(id);
        PlayerBankAccountRepository.getInstance().deleteById(id);
    }
}
