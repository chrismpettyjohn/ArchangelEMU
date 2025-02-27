package com.us.archangel.gang.context;

import com.us.nova.core.GenericContext;
import com.us.archangel.gang.model.GangRoleModel;

public class GangRoleContext extends GenericContext<GangRoleModel> {

    private static volatile GangRoleContext instance;

    public static synchronized GangRoleContext getInstance() {
        if (instance == null) {
            instance = new GangRoleContext();
        }
        return instance;
    }

    protected GangRoleContext() {
        super(GangRoleModel.class);
    }

    public GangRoleModel getByGangIdAndOrderId(int gangId, int orderId) {
        return getAll().values().stream()
                .filter(model -> model.getGangId() == gangId && model.getOrderId() == orderId)
                .findFirst()
                .orElse(null);
    }
}
