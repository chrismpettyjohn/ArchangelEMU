package com.us.archangel.player.entity;

import com.us.archangel.ammo.entity.AmmoEntity;
import com.us.archangel.weapon.entity.WeaponEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_players_weapons", indexes = {
        @Index(name = "idx_users_id", columnList = "users_id"),
        @Index(name = "idx_weapon_id", columnList = "weapons_id"),
        @Index(name = "idx_ammo_id", columnList = "ammo_id")
})
public class PlayerWeaponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "users_id", nullable = false)
    private int userId;

    @Column(name = "weapons_id", nullable = false)
    private int weaponId;

    @Column(name = "ammo_id", nullable = false)
    private int ammoId;

    @Column(name = "ammo_remaining", nullable = false)
    private int ammoRemaining;

    @ManyToOne
    @JoinColumn(name = "weapons_id", nullable = false, insertable=false, updatable=false)
    private WeaponEntity weapon;

    @ManyToOne
    @JoinColumn(name = "ammo_id", nullable = false, insertable=false, updatable=false)
    private AmmoEntity ammo;
}
