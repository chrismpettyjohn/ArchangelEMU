package com.us.roleplay.users.inventory;

import com.eu.habbo.habbohotel.users.Habbo;
import com.us.roleplay.users.models.StoreProduct;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StoreShiftComponent {
    private final Habbo habbo;
    private final List<StoreProduct> storeProducts = new ArrayList<>();

    public StoreShiftComponent(Habbo habbo) {
        this.habbo = habbo;
    }

    public void addProduct(StoreProduct product) {
        storeProducts.add(product);
    }

    public void removeProductById(int id) {
        storeProducts.removeIf(product -> product.getId() == id);
    }

    public void clearProducts() {
        storeProducts.clear();
    }
}
