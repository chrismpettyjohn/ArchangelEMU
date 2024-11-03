package com.us.archangel.player.service;

import com.us.archangel.player.context.PlayerBankAccountContext;
import com.us.archangel.player.entity.PlayerBankAccountEntity;
import com.us.archangel.player.mapper.PlayerBankAccountMapper;
import com.us.archangel.player.model.PlayerBankAccountModel;
import com.us.archangel.player.repository.PlayerBankAccountRepository;
import com.us.nova.core.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerBankAccountService extends GenericService<PlayerBankAccountModel, PlayerBankAccountContext, PlayerBankAccountRepository> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerBankAccountService.class);

    private static PlayerBankAccountService instance;

    public static synchronized PlayerBankAccountService getInstance() {
        if (instance == null) {
            instance = new PlayerBankAccountService();
        }
        return instance;
    }

    private PlayerBankAccountService() {
        super(PlayerBankAccountContext.getInstance(), PlayerBankAccountRepository.getInstance(), PlayerBankAccountMapper.class, PlayerBankAccountEntity.class);
        LOGGER.info("Player Bank Account Service > starting");
        this.getAll();  // Preload all bank accounts
        LOGGER.info("Player Bank Account Service > loaded {} bank accounts", this.getAll().size());
    }

    public PlayerBankAccountModel getByUserIdAndCorpId(int userId, int corpId) {
        PlayerBankAccountModel cachedModel = context.getAll().values().stream()
                .filter(model -> model.getUserId() == userId && model.getCorpId() == corpId)
                .findFirst()
                .orElse(null);

        if (cachedModel != null) {
            return cachedModel;
        }

        PlayerBankAccountEntity entity = repository.getByUserIdAndCorpId(userId, corpId);
        if (entity != null) {
            PlayerBankAccountModel model = PlayerBankAccountMapper.toModel(entity);
            context.add(entity.getId(), model);
            return model;
        }
        return null;
    }
}
