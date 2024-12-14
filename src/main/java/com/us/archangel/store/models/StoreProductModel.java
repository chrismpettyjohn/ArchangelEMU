package com.us.archangel.store.models;

import com.us.archangel.store.enums.StoreProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StoreProductModel {
    private int id;
    private StoreProductType type;

    public StoreProductModel() { }
}