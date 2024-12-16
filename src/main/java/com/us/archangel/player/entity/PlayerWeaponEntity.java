package com.us.archangel.player.entity;

import com.us.archangel.ammo.entity.AmmoEntity;
import com.us.archangel.weapon.entity.WeaponEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_players_weapons")
public class PlayerWeaponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "users_id", nullable = false)
    private int userId;

    @Column(name = "weapons_id", nullable = false)
    private int weaponId;

    @Column(name = "ammo_remaining", nullable = false)
    private int ammoRemaining;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ammo_id")
    private AmmoEntity ammo;

    @Column(name = "ammo_id", insertable = false, updatable = false)
    private int ammoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weapons_id", insertable = false, updatable = false)
    private WeaponEntity weapon;

    public void setAmmo(AmmoEntity ammo) {
        this.ammo = ammo;
        this.ammoId = ammo != null ? ammo.getId() : 0;
    }
}