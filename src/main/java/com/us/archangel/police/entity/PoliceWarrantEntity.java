package com.us.archangel.police.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archange_police_warrants")
public class PoliceWarrantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "crime_id", nullable = false)
    private int crimeId;

    @Column(name = "suspect_user_id", nullable = false)
    private int suspectUserId;

    @Column(name = "officer_user_id")
    private int officerUserId;
}
