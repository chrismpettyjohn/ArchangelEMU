package com.us.nova.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "nova_users_sso")
public class UserSSOEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "users_id", nullable = false)
    private int userId;

    @Column(name = "sso_token", nullable = false)
    private String ssoToken;

    @Column(name = "expires_at", nullable = false)
    private int expiresAt;

    @Column(name = "activated_at")
    private Integer activatedAt;

    @Column(name = "ip_address")
    private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false, insertable=false, updatable=false)
    private UserEntity user;

}
