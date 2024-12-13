package com.us.archangel.weapon.enums;

import lombok.Getter;

@Getter
public enum WeaponEffect {
    BLEED("bleed"),
    HEAL("heal"),
    NONE("none");

    // Getter to retrieve the string value of the enum
    private final String typeName;

    // Constructor for WeaponType enum
    WeaponEffect(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public static WeaponEffect fromString(String typeName) {
        for (WeaponEffect type : WeaponEffect.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with typeName: " + typeName);
    }

}
