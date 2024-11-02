package com.us.archangel.gang.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GangInviteModel {

    private int id;
    private int gangId;
    private int gangRoleId;
    private int userId;

    public GangInviteModel() {
    }
}
