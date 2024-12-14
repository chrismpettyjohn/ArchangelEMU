package com.us.archangel.store.inventory;

import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.store.models.StoreProductModel;
import com.us.archangel.store.models.StoreProductOfferModel;

import java.util.ArrayList;
import java.util.List;

public class StoreProductOffersComponent {
    private final Habbo habbo;
    private final List<StoreProductOfferModel> storeProductOfferModels = new ArrayList<>();

    public StoreProductOffersComponent(Habbo habbo) {
        this.habbo = habbo;
    }

    public void addOffer(StoreProductOfferModel product) {
        this.storeProductOfferModels.add(product);
    }

    public StoreProductOfferModel getOfferById(int id) {
        for (StoreProductOfferModel product : this.storeProductOfferModels) {
            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    public void removeOfferById(int id) {
        this.storeProductOfferModels.removeIf(product -> product.getId() == id);
    }

    public void clearOffers() {
        this.storeProductOfferModels.clear();
    }
}
