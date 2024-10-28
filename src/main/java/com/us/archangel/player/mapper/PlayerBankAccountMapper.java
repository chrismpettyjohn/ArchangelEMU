package com.us.archangel.player.mapper;

import com.us.archangel.player.entity.PlayerBankAccountEntity;
import com.us.archangel.player.model.PlayerBankAccountModel;

public class PlayerBankAccountMapper {

    public static PlayerBankAccountModel toModel(PlayerBankAccountEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PlayerBankAccountModel(
                entity.getId(),
                entity.getUserId(),
                entity.getCorpId(),
                entity.getAccountBalance()
        );
    }

    public static PlayerBankAccountEntity toEntity(PlayerBankAccountModel model) {
        if (model == null) {
            return null;
        }
        PlayerBankAccountEntity entity = new PlayerBankAccountEntity();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        entity.setCorpId(model.getCorpId());
        entity.setAccountBalance(model.getAccountBalance());
        return entity;
    }
}