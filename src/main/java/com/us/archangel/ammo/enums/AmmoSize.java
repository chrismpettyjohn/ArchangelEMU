package com.us.archangel.ammo.enums;

import lombok.Getter;

@Getter
public enum AmmoSize {
    NONE("none"),
    _556_45("556x45"),
    _762_39("762x39"),
    _762_51("762x51"),
    _9mm("9mm"),
    _45ACP("45acp"),
    _12g("12g"),
    HEALTH_SYRINGE("health_syringe"),
    STUN_CARTRIDGE("stun_cartridge");

    // Getter to retrieve the string value of the enum
    private final String typeName;

    // Constructor for WeaponType enum
    AmmoSize(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public static AmmoSize fromString(String typeName) {
        for (AmmoSize type : AmmoSize.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with typeName: " + typeName);
    }

}
