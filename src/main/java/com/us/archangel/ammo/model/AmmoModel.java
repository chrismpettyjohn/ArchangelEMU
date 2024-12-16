package com.us.archangel.ammo.model;

import com.us.archangel.ammo.enums.AmmoSize;
import com.us.archangel.ammo.enums.AmmoType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AmmoModel {
    private int id;
    private String displayName;
    private String uniqueName;
    private AmmoSize size;
    private AmmoType type;
    private int value;

    public AmmoModel() {
    }
}
