package com.us.archangel.store.inventory;

import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.store.models.StoreProductModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StoreShiftComponent {
    private final Habbo habbo;
    private final List<StoreProductModel> storeProductModels = new ArrayList<>();

    public StoreShiftComponent(Habbo habbo) {
        this.habbo = habbo;
    }

    public void addProduct(StoreProductModel product) {
        storeProductModels.add(product);
    }

    public StoreProductModel getProductById(int id) {
        for (StoreProductModel product : storeProductModels) {
            if (product.id() == id) {
                return product;
            }
        }

        return null;
    }

    public void removeProductById(int id) {
        storeProductModels.removeIf(product -> product.id() == id);
    }

    public void clearProducts() {
        storeProductModels.clear();
    }
}
