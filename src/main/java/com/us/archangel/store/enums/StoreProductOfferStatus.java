package com.us.archangel.store.enums;

import lombok.Getter;

@Getter
public enum StoreProductOfferStatus {
    PENDING("pending"),
    ACCEPTED("accepted"),
    REJECTED("rejected");
    private final String typeName;

    StoreProductOfferStatus(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public static StoreProductOfferStatus fromString(String typeName) {
        for (StoreProductOfferStatus type : StoreProductOfferStatus.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with typeName: " + typeName);
    }

}
