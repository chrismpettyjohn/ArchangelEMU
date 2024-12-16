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
                entity.getEmployeeUserId(),
                entity.getRecipientUserId(),
                entity.getProductId(),
                entity.getProductCost(),
                entity.getProductType(),
                entity.getOfferStatus()
        );
    }

    public static StoreProductOfferEntity toEntity(StoreProductOfferModel model) {
        if (model == null) {
            return null;
        }
        StoreProductOfferEntity entity = new StoreProductOfferEntity();
        entity.setId(model.getId());
        entity.setEmployeeUserId(model.getEmployeeUserId());
        entity.setRecipientUserId(model.getRecipientUserId());
        entity.setProductId(model.getProductId());
        entity.setProductCost(model.getProductCost());
        entity.setProductType(model.getProductType());
        entity.setOfferStatus(model.getOfferStatus());
        return entity;
    }
}