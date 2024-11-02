package com.us.archangel.sanction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SanctionModel {

    private int id;
    private String displayName;
    private String description;
    private Boolean isCriminal;
    private int criminalSentenceInSeconds;
    private Boolean isCivil;
    private int civilFineInCredits;

    public SanctionModel() {
    }
}
