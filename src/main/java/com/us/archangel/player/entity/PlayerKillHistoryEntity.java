package com.us.archangel.player.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "archangel_players_kills", indexes = {
        @Index(name = "idx_attacker_users_id", columnList = "attacker_users_id"),
        @Index(name = "idx_victim_users_id", columnList = "victim_users_id"),
        @Index(name = "idx_attacker_weapons_id", columnList = "attacker_weapons_id"),
})
public class PlayerKillHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "attacker_users_id", nullable = false)
    private int attackerUserId;

    @Column(name = "victim_users_id", nullable = false)
    private int victimUserId;

    @Column(name = "attacker_weapons_id", nullable = false)
    private int attackerWeaponId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
