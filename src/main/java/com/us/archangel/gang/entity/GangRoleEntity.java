package com.us.archangel.gang.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_gangs_roles")
public class GangRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "gangs_id", nullable = false)
    private int gangId;

    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "gangs_id", nullable = false, insertable=false, updatable=false)
    private GangEntity gang;
}
