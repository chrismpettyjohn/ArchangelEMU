package com.us.archangel.gang.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.gang.model.GangRoleModel;

public class GangRoleContext extends GenericContext<GangRoleModel> {

    private static volatile GangRoleContext instance;

    public static GangRoleContext getInstance() {
        if (instance == null) {
            synchronized (GangRoleContext.class) {
                if (instance == null) {
                    instance = new GangRoleContext();
                }
            }
        }
        return instance;
    }

    protected GangRoleContext() {
        super();
    }

}
