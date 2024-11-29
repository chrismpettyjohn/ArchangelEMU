package com.us.archangel.gang.model;

import com.us.archangel.gang.service.GangService;
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
    private int colorPrimary;
    private int colorSecondary;
    private int userId;
    private int roomId;

    public GangModel() {
    }

    public void update() {
        GangService.getInstance().update(this.id, this);
    }

}