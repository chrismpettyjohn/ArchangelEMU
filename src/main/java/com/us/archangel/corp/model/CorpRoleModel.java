package com.us.archangel.corp.model;

import com.us.archangel.corp.service.CorpRoleService;
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
    private String displayName;
    private String description;
    private String motto;
    private int salary;
    private String maleFigure;
    private String femaleFigure;
    private boolean canHire;
    private boolean canFire;
    private boolean canPromote;
    private boolean canDemote;
    private boolean canWorkAnywhere;

    public void save() {
        CorpRoleService.getInstance().update(this.id, this);
    }

    public CorpRoleModel() {
    }

    public void update() {
        CorpRoleService.getInstance().update(this.id, this);
    }
}
