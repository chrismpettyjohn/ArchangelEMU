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

    @Column(name = "users_id")
    private int userId;

    @Column(name = "strength_xp")
    private int strengthXp;;

    @Column(name = "melee_xp")
    private int meleeXp;

    @Column(name = "weapon_xp")
    private int weaponXp;

    @Column(name = "farming_xp")
    private int farmingXp;

    @Column(name = "mining_xp")
    private int miningXp;

    @Column(name = "fishing_xp")
    private int fishingXp;


}
