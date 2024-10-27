package com.us.archangel.player.entity;

import com.us.archangel.player.enums.PlayerAction;
import com.us.archangel.player.enums.converter.PlayerActionConverter;
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

    @Column(name = "users_id", nullable = false)
    private int userId;

    @Column(name = "gangs_id")
    private Integer gangId;;

    @Column(name = "gangs_roles_id")
    private Integer gangRoleId;

    @Column(name = "corps_id", nullable = false)
    private int corpId;

    @Column(name = "corps_roles_id", nullable = false)
    private int corpRoleId;

    @Column(name = "health_now", nullable = false)
    private int healthNow;

    @Column(name = "health_max", nullable = false)
    private int healthMax;

    @Column(name = "energy_now", nullable = false)
    private int energyNow;

    @Column(name = "energy_max", nullable = false)
    private int energyMax;

    @Column(name = "armor_now", nullable = false)
    private int armorNow;

    @Column(name = "armor_max", nullable = false)
    private int armorMax;

    @Column(name = "hunger_now", nullable = false)
    private int hungerNow;

    @Column(name = "hunger_max", nullable = false)
    private int hungerMax;

    @Column(name = "last_pos_x", nullable = false)
    private short lastPosX;

    @Column(name = "last_pos_y", nullable = false)
    private short lastPosY;

    @Column(name = "work_time_remaining_secs", nullable = false)
    private long workTimeRemainingSecs;

    @Column(name = "combat_delay_remaining_secs", nullable = false)
    private long combatDelayRemainingSecs;

    @Column(name = "jail_time_remaining_secs", nullable = false)
    private long jailTimeRemainingSecs;

    @Column(name = "current_action", nullable = false)
    @Convert(converter = PlayerActionConverter.class)
    private PlayerAction currentAction;

    @Column(name = "escorting_player_id")
    private Integer escortingPlayerId;
}
