package com.us.archangel.store.repository;

import com.us.archangel.store.entity.StoreProductOfferEntity;
import com.us.nova.core.GenericRepository;

import java.util.List;

public class StoreProductOfferRepository extends GenericRepository<StoreProductOfferEntity> {

    private static StoreProductOfferRepository instance;

    public static StoreProductOfferRepository getInstance() {
        if (instance == null) {
            instance = new StoreProductOfferRepository();
        }
        return instance;
    }
    private StoreProductOfferRepository() {
        super(StoreProductOfferEntity.class);
    }

    public StoreProductOfferEntity create(StoreProductOfferEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, StoreProductOfferEntity entity) {
        super.updateById(id, entity);
    }

    public StoreProductOfferEntity getById(int id) {
        return super.getById(id);
    }

    public List<StoreProductOfferEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

}
