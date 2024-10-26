package com.us.archangel.corp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_corps_invites", indexes = {
        @Index(name = "idx_users_id", columnList = "users_id"),
        @Index(name = "idx_corps_id", columnList = "corps_id")
})
public class CorpInviteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "corps_id", nullable = false)
    private int corpId;

    @Column(name = "corps_roles_id", nullable = false)
    private int corpRoleId;

    @Column(name = "users_id", nullable = false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "corps_id", nullable = false, insertable=false, updatable=false)
    private CorpEntity corp;

    @ManyToOne
    @JoinColumn(name = "corps_roles_id", nullable = false, insertable=false, updatable=false)
    private CorpRoleEntity corpRole;
}
