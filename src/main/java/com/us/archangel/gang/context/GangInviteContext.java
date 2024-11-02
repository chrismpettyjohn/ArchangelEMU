package com.us.archangel.gang.context;

import com.us.nova.core.GenericContext;
import com.us.archangel.gang.model.GangInviteModel;

public class GangInviteContext extends GenericContext<GangInviteModel> {

    private static volatile GangInviteContext instance;

    public static synchronized GangInviteContext getInstance() {
        if (instance == null) {
            instance = new GangInviteContext();
        }
        return instance;
    }

    protected GangInviteContext() {
        super();
    }
}
