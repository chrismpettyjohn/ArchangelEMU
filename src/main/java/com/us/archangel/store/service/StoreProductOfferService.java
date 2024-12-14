package com.us.archangel.store.service;

import com.us.archangel.store.context.StoreProductOfferContext;
import com.us.archangel.store.entity.StoreProductOfferEntity;
import com.us.archangel.store.mapper.StoreProductOfferMapper;
import com.us.archangel.store.models.StoreProductOfferModel;
import com.us.archangel.store.repository.StoreProductOfferRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class StoreProductOfferService extends GenericService<StoreProductOfferModel, StoreProductOfferContext, StoreProductOfferRepository> {

    private static StoreProductOfferService instance;

    public static StoreProductOfferService getInstance() {
        if (instance == null) {
            instance = new StoreProductOfferService();
        }
        return instance;
    }

    private StoreProductOfferService() {
        super(StoreProductOfferContext.getInstance(), StoreProductOfferRepository.getInstance(), StoreProductOfferMapper.class, StoreProductOfferEntity.class);
    }

    public StoreProductOfferModel create(StoreProductOfferModel model) {
        return super.create(model);
    }

    public void update(int id, StoreProductOfferModel model) {
        super.update(id, model);
    }

    public List<StoreProductOfferModel> getAll() {
        return super.getAll();
    }

    public StoreProductOfferModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }

}
