package com.us.archangel.gang.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_gangs")
public class GangEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "room_id")
    private int roomId;
}
