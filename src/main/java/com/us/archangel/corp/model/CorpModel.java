package com.us.archangel.corp.model;

import com.us.archangel.corp.enums.CorpIndustry;
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
    private String description;
    private String badge;
    private CorpSector sector;
    private CorpIndustry industry;
    private int userId;
    private int roomId;

    public CorpModel() {
    }

}