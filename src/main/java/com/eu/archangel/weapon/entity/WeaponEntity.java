package com.eu.archangel.weapon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rp_weapons")
public class WeaponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id")
    private int id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "unique_name", nullable = false)
    private String uniqueName;

    @Column(name = "type")
    private String type;

    @Column(name = "min_damage", nullable = false)
    private int minDamage;

    @Column(name = "max_damage", nullable = false)
    private int maxDamage;

    @Column(name = "range_in_tiles")
    private int rangeInTiles;

    @Column(name = "accuracy")
    private int accuracy;

    @Column(name = "reload_time")
    private int reloadTime;

    @Column(name = "reload_message")
    private String reloadMessage;

    @Column(name = "ammo_capacity")
    private int ammoCapacity;

    @Column(name = "weight")
    private int weight;

    @Column(name = "cooldown_seconds")
    private int cooldownSeconds;

    @Column(name = "special_abilities")
    private String specialAbilities;

    @Column(name = "equip_effect")
    private int equipEffect;

    @Column(name = "equip_message")
    private String equipMessage;

    @Column(name = "unequip_message")
    private String unequipMessage;

    @Column(name = "attack_message")
    private String attackMessage;
}
