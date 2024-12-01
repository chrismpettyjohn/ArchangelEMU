package com.us.archangel.player.entity;

import com.us.archangel.ammo.entity.AmmoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_players_ammo", indexes = {
        @Index(name = "idx_users_id", columnList = "users_id"),
        @Index(name = "idx_ammo_id", columnList = "ammo_id")
})
public class PlayerAmmoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "users_id", nullable = false)
    private int userId;

    @Column(name = "ammo_id", nullable = false)
    private int ammoId;

    @Column(name = "ammo_remaining", nullable = false)
    private int ammoRemaining;

    @ManyToOne
    @JoinColumn(name = "ammo_id", nullable = false, insertable=false, updatable=false)
    private AmmoEntity ammo;
}
