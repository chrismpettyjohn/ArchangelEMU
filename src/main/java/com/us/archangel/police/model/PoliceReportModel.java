package com.us.archangel.police.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PoliceReportModel {

    private int id;
    private int witnessUserId;
    private int suspectUserId;
    private Integer officerUserId;
    private int lawId;
    private String description;

}