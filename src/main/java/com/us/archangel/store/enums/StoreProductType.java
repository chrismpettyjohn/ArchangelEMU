package com.us.archangel.store.enums;

import lombok.Getter;

@Getter
public enum StoreProductType {
    AMMO("ammo"),
    WEAPON("weapon"),
    ITEM("item");
    private final String typeName;

    StoreProductType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public static StoreProductType fromString(String typeName) {
        for (StoreProductType type : StoreProductType.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with typeName: " + typeName);
    }

}
