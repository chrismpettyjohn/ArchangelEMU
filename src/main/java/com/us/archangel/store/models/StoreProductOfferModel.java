package com.us.archangel.store.models;

import com.us.archangel.store.enums.StoreProductOfferStatus;
import com.us.archangel.store.enums.StoreProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class StoreProductOfferModel {
    private int id;
    private int recipientUserId;
    private int employeeUserId;
    private int productCost;
    private int productId;
    private StoreProductType productType;
    private StoreProductOfferStatus offerStatus;

    public StoreProductOfferModel() {

    }
}
