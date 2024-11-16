package com.us.archangel.corp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_corps_roles", indexes = {
        @Index(name = "idx_corp_id", columnList = "corps_id"),
        @Index(name = "idx_order_id", columnList = "order_id"),
        @Index(name = "idx_display_name", columnList = "display_name")
})
public class CorpRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "corps_id", nullable = false)
    private int corpId;

    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Column(name = "display_name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "motto", nullable = false)
    private String motto;

    @Column(name = "salary", nullable = false)
    private int salary;

    @Column(name = "male_figure", nullable = false)
    private String maleFigure;

    @Column(name = "female_figure", nullable = false)
    private String femaleFigure;

    @Column(name = "can_hire", nullable = false)
    private int canHire;

    @Column(name = "can_fire", nullable = false)
    private int canFire;

    @Column(name = "can_promote", nullable = false)
    private int canPromote;

    @Column(name = "can_demote", nullable = false)
    private int canDemote;

    @Column(name = "can_work_anywhere", nullable = false)
    private int canWorkAnywhere;

    @ManyToOne
    @JoinColumn(name = "corps_id", nullable = false, insertable=false, updatable=false)
    private CorpEntity corp;
}
