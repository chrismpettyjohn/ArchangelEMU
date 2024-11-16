package com.us.archangel.government.context;

import com.us.archangel.government.model.GovernmentLawModel;
import com.us.nova.core.GenericContext;

public class GovernmentLawContext extends GenericContext<GovernmentLawModel> {

    private static volatile GovernmentLawContext instance;

    public static synchronized GovernmentLawContext getInstance() {
        if (instance == null) {
            instance = new GovernmentLawContext();
        }
        return instance;
    }

    protected GovernmentLawContext() {
        super(GovernmentLawModel.class);
    }
}
