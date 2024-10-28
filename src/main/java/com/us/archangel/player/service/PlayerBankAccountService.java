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

    public void create(PlayerBankAccountEntity playerEntity, PlayerBankAccountModel playerModel) {
        playerBankAccountContext.add(playerEntity.getId(), playerModel);
        PlayerBankAccountRepository.getInstance().create(playerEntity);
    }

    public void update(int id, PlayerBankAccountEntity updatedPlayerBankAccount) {
        playerBankAccountContext.update(id, PlayerBankAccountMapper.toModel(updatedPlayerBankAccount));
        PlayerBankAccountRepository.getInstance().updateById(id, updatedPlayerBankAccount);
    }

    public PlayerBankAccountModel getByUserID(int userID) {
        PlayerBankAccountModel storedVal = playerBankAccountContext.get(userID);
        if (storedVal != null) {
            return storedVal;
        }

        PlayerBankAccountEntity entity = PlayerBankAccountRepository.getInstance().getByUserId(userID);
        if (entity == null) {
            return null;
        }

        PlayerBankAccountModel model = PlayerBankAccountMapper.toModel(entity);
        playerBankAccountContext.add(entity.getId(), model);
        return model;
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
