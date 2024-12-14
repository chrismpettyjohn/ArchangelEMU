package com.us.archangel.store;

import com.us.archangel.ammo.repository.AmmoRepository;
import com.us.archangel.store.context.StoreProductOfferContext;
import com.us.archangel.store.models.StoreProductOfferModel;
import com.us.archangel.store.repository.StoreProductOfferRepository;
import com.us.archangel.store.service.StoreProductOfferService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Getter
public class StoreManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreManager.class);

    private static StoreManager instance;

    public static StoreManager getInstance() {
        if (instance == null) {
            instance = new StoreManager();
        }
        return instance;
    }

    private final StoreProductOfferRepository storeProductOfferRepository;
    private final StoreProductOfferContext storeProductOfferContext;
    private final StoreProductOfferService storeProductOfferService;

    private StoreManager() {
        this.storeProductOfferContext = StoreProductOfferContext.getInstance();
        this.storeProductOfferRepository = StoreProductOfferRepository.getInstance();
        this.storeProductOfferService = StoreProductOfferService.getInstance();
        this.load();
    }

    public void load() {
        LOGGER.info("Store manager > starting");

        this.storeProductOfferService.getInstance().getAll();

        LOGGER.info("Store manager > loaded");
    }

    public void dispose() {
        for (StoreProductOfferModel storeProductOfferModel : this.storeProductOfferContext.getAll().values()) {
            this.storeProductOfferContext.updateById(storeProductOfferModel.getId(), AmmoMapper.toEntity(storeProductOfferModel));
            this.storeProductOfferContext.delete(storeProductOfferModel.getId());
        }
        LOGGER.info("Ammo manager > disposed");
    }
}
