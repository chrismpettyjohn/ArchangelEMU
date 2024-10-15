package com.us.archangel.corp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_corp_roles")
public class CorpRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "corp_id", nullable = false)
    private int corpID;

    @Column(name = "order_id", nullable = false)
    private int orderID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "motto")
    private String motto;

    @Column(name = "salary", nullable = false)
    private int salary;

    @Column(name = "male_figure")
    private String maleFigure;

    @Column(name = "female_figure")
    private String femaleFigure;

    @Column(name = "can_hire", nullable = false)
    private boolean canHire;

    @Column(name = "can_fire", nullable = false)
    private boolean canFire;

    @Column(name = "can_promote", nullable = false)
    private boolean canPromote;

    @Column(name = "can_demote", nullable = false)
    private boolean canDemote;

    @Column(name = "can_work_anywhere", nullable = false)
    private boolean canWorkAnywhere;
}
