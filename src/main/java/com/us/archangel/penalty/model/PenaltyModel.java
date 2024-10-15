package com.us.archangel.penalty.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PenaltyModel {

    private int id;
    private String displayName;
    private String description;
    private Boolean isCriminal;
    private int criminalSentenceInSeconds;
    private Boolean isCivil;
    private int civilFineInCredits;


}
