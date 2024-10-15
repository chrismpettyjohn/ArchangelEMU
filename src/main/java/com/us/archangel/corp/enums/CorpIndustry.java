package com.us.archangel.corp.enums;

import lombok.Getter;

@Getter
public enum CorpIndustry {
    Retail("retail"),
    Hospital("hospital"),
    Paramedic("paramedic"),
    Police("police");

    // Getter to retrieve the string value of the enum
    private final String typeName;

    // Constructor for WeaponType enum
    CorpIndustry(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public static CorpIndustry fromString(String typeName) {
        for (CorpIndustry type : CorpIndustry.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with typeName: " + typeName);
    }

}
