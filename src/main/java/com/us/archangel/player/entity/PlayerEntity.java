package com.us.archangel.player.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_players")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "users_id")
    private int userId;

    @Column(name = "gangs_id")
    private int gangId;;

    @Column(name = "gangs_roles_id")
    private int gangRoleId;

    @Column(name = "corps_id")
    private int corpId;

    @Column(name = "corps_roles_id")
    private int corpRoleId;

    @Column(name = "health_now")
    private int healthNow;

    @Column(name = "health_max")
    private int healthMax;

    @Column(name = "energy_now")
    private int energyNow;

    @Column(name = "energy_max")
    private int energyMax;

    @Column(name = "energy_now")
    private int armorNow;

    @Column(name = "armor_max")
    private int armorMax;

    @Column(name = "last_pos_x")
    private int lastPosX;

    @Column(name = "last_pos_y")
    private int lastPosY;


}
