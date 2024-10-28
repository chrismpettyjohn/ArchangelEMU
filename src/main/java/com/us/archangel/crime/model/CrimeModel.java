package com.us.archangel.crime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CrimeModel {

    private int id;
    private String displayName;
    private String description;
    private int jailTimeSeconds;

}