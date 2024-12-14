package com.us.archangel.store.mapper;

import com.us.archangel.store.entity.StoreProductOfferEntity;
import com.us.archangel.store.models.StoreProductOfferModel;

public class StoreProductOfferMapper {

    public static StoreProductOfferModel toModel(StoreProductOfferEntity entity) {
        if (entity == null) {
            return null;
        }
        return new StoreProductOfferModel(
                entity.getId(),
                entity.getEmployeePlayerId(),
                entity.getRecipientPlayerId(),
                entity.getStoreProductId(),
                entity.getStoreProductCost(),
                entity.getStoreProductType(),
                entity.getOfferStatus()
        );
    }

    public static StoreProductOfferEntity toEntity(StoreProductOfferModel model) {
        if (model == null) {
            return null;
        }
        StoreProductOfferEntity entity = new StoreProductOfferEntity();
        entity.setId(model.getId());
        entity.setEmployeePlayerId(model.getEmployeeUserId());
        entity.setRecipientPlayerId(model.getRecipientUserId());
        entity.setStoreProductId(model.getProductId());
        entity.setStoreProductCost(model.getProductCost());
        entity.setStoreProductType(model.getProductType());
        entity.setOfferStatus(model.getOfferStatus());
        return entity;
    }
}