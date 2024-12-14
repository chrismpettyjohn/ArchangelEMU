package com.us.archangel.store.models;

import com.us.archangel.store.enums.StoreProductOfferStatus;
import com.us.archangel.store.enums.StoreProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class StoreProductOfferModel {
    private final int id;
    @Setter
    private int recipientUserId;
    @Setter
    private int employeeUserId;
    @Setter
    private int productCost;
    @Setter
    private int productId;
    @Setter
    private StoreProductType productType;
    @Setter
    private StoreProductOfferStatus offerStatus;
}
