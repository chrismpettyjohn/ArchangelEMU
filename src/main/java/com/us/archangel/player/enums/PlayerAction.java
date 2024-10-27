package com.us.archangel.player.enums;

import lombok.Getter;

@Getter
public enum PlayerAction {
    Stunned("stunned"),
    Cuffed("cuffed"),
    Escorted("escorted"),
    Escorting("escorting"),
    None("none");

    // Getter to retrieve the string value of the enum
    private final String typeName;

    // Constructor for WeaponType enum
    PlayerAction(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public static PlayerAction fromString(String typeName) {
        for (PlayerAction type : PlayerAction.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with typeName: " + typeName);
    }

}
