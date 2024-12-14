package com.us.archangel.store.context;

import com.us.archangel.store.models.StoreProductOfferModel;
import com.us.nova.core.GenericContext;

public class StoreProductOfferContext extends GenericContext<StoreProductOfferModel> {

    private static volatile StoreProductOfferContext instance;

    public static StoreProductOfferContext getInstance() {
        if (instance == null) {
            synchronized (StoreProductOfferContext.class) {
                if (instance == null) {
                    instance = new StoreProductOfferContext();
                }
            }
        }
        return instance;
    }

    protected StoreProductOfferContext() {
        super(StoreProductOfferModel.class);
    }
}
