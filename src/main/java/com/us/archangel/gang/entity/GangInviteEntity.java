package com.us.archangel.gang.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_gangs_invites", indexes = {
        @Index(name = "idx_users_id", columnList = "users_id"),
        @Index(name = "idx_gangs_id", columnList = "gangs_id")
})
public class GangInviteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "gangs_id", nullable = false)
    private int gangId;

    @Column(name = "gangs_roles_ids", nullable = false)
    private int gangRoleId;

    @Column(name = "users_id", nullable = false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "gangs_id", nullable = false, insertable=false, updatable=false)
    private GangEntity gang;

    @ManyToOne
    @JoinColumn(name = "gangs_roles_id", nullable = false, insertable=false, updatable=false)
    private GangRoleEntity gangRole;
}
