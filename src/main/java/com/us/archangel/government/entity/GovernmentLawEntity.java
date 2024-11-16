package com.us.archangel.government.entity;

import com.us.archangel.government.enums.LawStatus;
import com.us.archangel.government.enums.LawType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_government_laws")
public class GovernmentLawEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "proposed_by_user_id", nullable = false)
    private int proposedByUserId;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "status", nullable = false)
    private LawStatus status;

    @Column(name = "type", nullable = false)
    private LawType type;
}
