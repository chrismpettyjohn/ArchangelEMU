package com.us.archangel.police.context;

import com.us.nova.core.GenericContext;
import com.us.archangel.police.model.PoliceCrimeModel;

public class PoliceCrimeContext extends GenericContext<PoliceCrimeModel> {

    private static volatile PoliceCrimeContext instance;

    public static synchronized PoliceCrimeContext getInstance() {
        if (instance == null) {
            instance = new PoliceCrimeContext();
        }
        return instance;
    }

    protected PoliceCrimeContext() {
        super(PoliceCrimeModel.class);
    }
}
