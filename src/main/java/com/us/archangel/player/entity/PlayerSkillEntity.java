package com.us.archangel.player.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_players_skills")
public class PlayerSkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "users_id", nullable = false)
    private int userId;

    @Column(name = "strength_xp", nullable = false)
    private int strengthXp;;

    @Column(name = "melee_xp", nullable = false)
    private int meleeXp;

    @Column(name = "weapon_xp", nullable = false)
    private int weaponXp;

    @Column(name = "farming_xp", nullable = false)
    private int farmingXp;

    @Column(name = "mining_xp", nullable = false)
    private int miningXp;

    @Column(name = "fishing_xp", nullable = false)
    private int fishingXp;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private PlayerEntity player;


}
