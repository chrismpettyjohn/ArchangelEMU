package com.us.archangel.gang.context;

import com.us.nova.core.GenericContext;
import com.us.archangel.gang.model.GangModel;

public class GangContext extends GenericContext<GangModel> {

    private static volatile GangContext instance;

    public static synchronized GangContext getInstance() {
        if (instance == null) {
            instance = new GangContext();
        }
        return instance;
    }

    protected GangContext() {
        super(GangModel.class);
    }
}
