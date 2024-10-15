package com.us.archangel.corp.enums;

import lombok.Getter;

@Getter
public enum CorpSector {
    Government("government"),
    Private("private");

    // Getter to retrieve the string value of the enum
    private final String typeName;

    // Constructor for WeaponType enum
    CorpSector(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public static CorpSector fromString(String typeName) {
        for (CorpSector type : CorpSector.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with typeName: " + typeName);
    }

}
