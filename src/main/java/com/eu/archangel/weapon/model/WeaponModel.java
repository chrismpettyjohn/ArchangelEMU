package com.eu.archangel.weapon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WeaponModel {
    private int id;
    private String displayName;
    private String uniqueName;
    private String type;
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
}
