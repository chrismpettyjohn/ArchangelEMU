package com.us.archangel.police.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PoliceCrimeModel {

    private int id;
    private String displayName;
    private String description;
    private int jailTimeSeconds;

    public PoliceCrimeModel() {
    }

}