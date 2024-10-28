package com.us.archangel.sanction.context;

import com.us.nova.core.GenericContext;
import com.us.archangel.sanction.model.SanctionModel;

public class SanctionContext extends GenericContext<SanctionModel> {

    private static volatile SanctionContext instance;

    public static SanctionContext getInstance() {
        if (instance == null) {
            synchronized (SanctionContext.class) {
                if (instance == null) {
                    instance = new SanctionContext();
                }
            }
        }
        return instance;
    }

    private SanctionContext() {
        super();
    }
}
