package com.us.archangel.government.enums;

import lombok.Getter;

@Getter
public enum LawStatus {
    Draft("draft"),
    Proposed("proposed"),
    Rejected("rejected"),
    Passed("passed");

    // Getter to retrieve the string value of the enum
    private final String typeName;

    // Constructor for WeaponType enum
    LawStatus(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public static LawStatus fromString(String typeName) {
        for (LawStatus type : LawStatus.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with typeName: " + typeName);
    }

}
