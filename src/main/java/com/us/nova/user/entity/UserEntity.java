package com.us.nova.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "nova_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "real_name", nullable = false)
    private String realName;

    @Column(name = "password_hashed", nullable = false)
    private String passwordHashed;

    @Column(name = "email_address", nullable = false)
    private String email;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified;

    @Column(name = "last_login", nullable = false)
    private int lastLogin;

    @Column(name = "last_online", nullable = false)
    private int lastOnline;

    @Column(name = "created_at", nullable = false)
    private int createdAt;

    @Column(name = "updatedAt", nullable = false)
    private int updatedAt;

    @Column(name = "motto", nullable = false)
    private String motto;

    @Column(name = "look", nullable = false)
    private String look;


}
