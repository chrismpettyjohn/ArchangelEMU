package com.us.archangel.police.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PoliceWarrantModel {
    private int id;
    private int suspectUserId;
    private Integer officerUserId;
    private int lawId;
}