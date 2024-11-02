package com.us.archangel.weapon.model;

import com.us.archangel.weapon.enums.WeaponType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WeaponModel {
    private int id;
    private String displayName;
    private String uniqueName;
    private WeaponType type;
    private int minDamage;
    private int maxDamage;
    private int rangeInTiles;
    private int accuracy;
    private int reloadTime;
    private String reloadMessage;
    private int ammoCapacity;
    private int weight;
    private int cooldownSeconds;
    private String specialAbilities;
    private int equipEffect;
    private String equipMessage;
    private String unequipMessage;
    private String attackMessage;

    public WeaponModel() {
    }
}
