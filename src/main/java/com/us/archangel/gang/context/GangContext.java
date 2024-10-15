package com.us.archangel.gang.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.gang.model.GangModel;

public class GangContext extends GenericContext<GangModel> {

    private static GangContext instance;

    private GangContext() {
        super();
    }

    public static GangContext getInstance() {
        if (instance == null) {
            instance = new GangContext();
        }
        return instance;
    }
}
