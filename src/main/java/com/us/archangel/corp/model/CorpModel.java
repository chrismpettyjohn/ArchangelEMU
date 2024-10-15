package com.us.archangel.corp.model;

import com.us.archangel.corp.enums.CorpSector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CorpModel {

    private int id;
    private String displayName;
    private CorpSector sector;
    private CorpSector industry;
    private int userId;
    private int roomId;

}