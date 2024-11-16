package com.us.archangel.police.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_police_reports")
public class PoliceReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "witness_user_id", nullable = false)
    private int witnessUserId;

    @Column(name = "suspect_user_id", nullable = false)
    private int suspectUserId;

    @Column(name = "officer_user_id", nullable = false)
    private int officerUserId;

    @Column(name = "description", nullable = false)
    private String description;
}
