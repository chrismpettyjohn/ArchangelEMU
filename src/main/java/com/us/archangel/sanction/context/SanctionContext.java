package com.us.archangel.sanction.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.sanction.model.SanctionModel;

public class SanctionContext extends GenericContext<SanctionModel> {

    private static SanctionContext instance;

    private SanctionContext() {
        super();
    }

    public static SanctionContext getInstance() {
        if (instance == null) {
            instance = new SanctionContext();
        }
        return instance;
    }
}
