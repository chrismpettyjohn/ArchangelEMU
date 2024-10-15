package com.us.archangel.player.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerSkillModel {

    private int id;
    private int userId;
    private int strengthXp;
    private int meleeXp;
    private int weaponXp;
    private int farmingXp;
    private int miningXp;
    private int fishingXp;


}
