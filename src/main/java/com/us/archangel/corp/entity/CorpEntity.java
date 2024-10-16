package com.us.archangel.corp.entity;

import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.enums.CorpSector;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_corps")
public class CorpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "badge", nullable = false)
    private String badge;

    @Column(name = "sector")
    private CorpSector sector;

    @Column(name = "industry")
    private CorpIndustry industry;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "room_id")
    private int roomId;
}
