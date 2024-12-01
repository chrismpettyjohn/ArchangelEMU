package com.us.archangel.ammo.enums;

import lombok.Getter;

@Getter
public enum AmmoType {
    STANDARD("standard"),
    FMJ("fmj"),
    STUN("stun"),
    ARMOR_PIERCING("ap");

    // Getter to retrieve the string value of the enum
    private final String typeName;

    // Constructor for WeaponType enum
    AmmoType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public static AmmoType fromString(String typeName) {
        for (AmmoType type : AmmoType.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with typeName: " + typeName);
    }

}
