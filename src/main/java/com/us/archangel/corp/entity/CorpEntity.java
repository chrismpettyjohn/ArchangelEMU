package com.us.archangel.corp.entity;

import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.enums.CorpSector;
import com.us.archangel.corp.enums.converter.CorpIndustryConverter;
import com.us.archangel.corp.enums.converter.CorpSectorConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_corps", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_room_id", columnList = "room_id"),
        @Index(name = "idx_sector", columnList = "sector"),
        @Index(name = "idx_industry", columnList = "industry"),
        @Index(name = "idx_display_name", columnList = "display_name")
})
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

    @Column(name = "sector", nullable = false)
    @Convert(converter = CorpSectorConverter.class)
    private CorpSector sector;

    @Column(name = "industry")
    @Convert(converter = CorpIndustryConverter.class)
    private CorpIndustry industry;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "room_id", nullable = false)
    private int roomId;

    @Column(name = "created_at", nullable = false)
    private int createdAt;
}
