package com.us.archangel.player.mapper;

import com.us.archangel.player.entity.PlayerBankAccountEntity;
import com.us.archangel.player.model.PlayerBankAccountModel;
import com.us.nova.core.GenericMapper;

public class PlayerBankAccountMapper extends GenericMapper<PlayerBankAccountEntity, PlayerBankAccountModel> {

    public static PlayerBankAccountModel toModel(PlayerBankAccountEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PlayerBankAccountModel(
                entity.getId(),
                entity.getUserId(),
                entity.getCorpId(),
                entity.getAccountBalance(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
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
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());
        return entity;
    }
}
