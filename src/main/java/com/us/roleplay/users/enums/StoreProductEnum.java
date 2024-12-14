package com.us.roleplay.users.enums;

import lombok.Getter;

@Getter
public enum StoreProductEnum {
    AMMO("ammo"),
    WEAPON("weapon"),
    ITEM("item");

    private final String value;

    StoreProductEnum(String value) {
        this.value = value;
    }

}
