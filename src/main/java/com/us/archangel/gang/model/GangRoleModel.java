package com.us.archangel.gang.model;

import com.us.archangel.gang.service.GangRoleService;
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
    private boolean canInvite;
    private boolean canKick;

    public GangRoleModel() {
    }

    public void update() {
        GangRoleService.getInstance().update(this.id, this);
    }
}
