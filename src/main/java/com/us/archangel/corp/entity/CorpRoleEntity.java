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

    @ManyToOne
    @JoinColumn(name = "corps_id", nullable = false, insertable=false, updatable=false)
    private CorpEntity corp;
}
