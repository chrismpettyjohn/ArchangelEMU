package com.eu.archangel.corp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CorpRoleModel {

    private int id;
    private int corpId;
    private int orderId;
    private String name;
    private String motto;
    private int salary;
    private String maleFigure;
    private String femaleFigure;
    private boolean canHire;
    private boolean canFire;
    private boolean canPromote;
    private boolean canDemote;
    private boolean canWorkAnywhere;
}
