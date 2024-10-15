package com.us.archangel.gang.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GangRoleModel {

    private int id;
    private int gangId;
    private int orderId;
    private String name;
}
