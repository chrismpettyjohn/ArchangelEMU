package com.eu.archangel.corp.entity;

import com.eu.archangel.corp.enums.CorpSector;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rp_corporations")
public class CorpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "sector")
    private CorpSector sector;

    @Column(name = "industry")
    private CorpSector CorpIndustry;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "room_id")
    private int roomId;
}
