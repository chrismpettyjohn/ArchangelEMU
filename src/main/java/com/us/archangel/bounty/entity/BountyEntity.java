package com.us.archangel.bounty.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_bounties")
public class BountyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "crime_id", nullable = false)
    private int crimeId;

    @Column(name = "closed_at")
    private Integer closedAt;
}
