package com.us.archangel.police.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_crimes")
public class PoliceCrimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "jail_time_seconds", nullable = false)
    private int jailTimeSeconds;
}
