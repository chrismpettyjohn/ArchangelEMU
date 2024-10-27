package com.us.archangel.player.entity;

import com.us.archangel.weapon.entity.WeaponEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_players_weapons", indexes = {
        @Index(name = "idx_players_id", columnList = "players_id"),
        @Index(name = "idx_weapon_id", columnList = "weapon_id")
})
public class PlayerWeaponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "players_id", nullable = false)
    private int playerId;

    @Column(name = "weapons_id", nullable = false)
    private int weaponId;

    @Column(name = "ammo_remaining", nullable = false)
    private int ammoRemaining;

    @ManyToOne
    @JoinColumn(name = "players_id", nullable = false, insertable=false, updatable=false)
    private PlayerEntity player;

    @ManyToOne
    @JoinColumn(name = "weapons_id", nullable = false, insertable=false, updatable=false)
    private WeaponEntity weapon;
}
