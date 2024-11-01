package com.us.nova.betacode.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "nova_users")
public class BetaCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "users_id")
    private Integer userId;

    @Column(name = "claimed_at")
    private Integer claimedAt;

    @Column(name = "created_at", nullable = false)
    private int createdAt;


}
