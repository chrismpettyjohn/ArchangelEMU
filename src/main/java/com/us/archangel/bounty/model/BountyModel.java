package com.us.archangel.bounty.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BountyModel {

    private int id;
    private int userId;
    private int crimeId;

}