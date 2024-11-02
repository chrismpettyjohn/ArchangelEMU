package com.us.archangel.gang.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GangModel {

    private int id;
    private String displayName;
    private String description;
    private String badge;
    private int userId;
    private int roomId;

    public GangModel() {
    }

}