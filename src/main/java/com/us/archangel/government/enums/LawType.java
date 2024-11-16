package com.us.archangel.government.enums;

import lombok.Getter;

@Getter
public enum LawType {
    Civil("civil"),
    Criminal("criminal");

    // Getter to retrieve the string value of the enum
    private final String typeName;

    // Constructor for WeaponType enum
    LawType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public static LawType fromString(String typeName) {
        for (LawType type : LawType.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with typeName: " + typeName);
    }

}
