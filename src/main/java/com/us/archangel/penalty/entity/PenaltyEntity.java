package com.us.archangel.penalty.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "archangel_penalties")
public class PenaltyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_criminal", nullable = false)
    private Boolean isCriminal;

    @Column(name = "criminal_sentence_secs", nullable = false)
    private int criminalSentenceInSeconds;

    @Column(name = "is_civil", nullable = false)
    private Boolean isCivil;

    @Column(name = "civil_fine", nullable = false)
    private int civilFineInCredits;
}
