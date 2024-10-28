package com.us.archangel.crime.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.crime.model.CrimeModel;

public class CrimeContext extends GenericContext<CrimeModel> {

    private static volatile CrimeContext instance;

    public static CrimeContext getInstance() {
        if (instance == null) {
            synchronized (CrimeContext.class) {
                if (instance == null) {
                    instance = new CrimeContext();
                }
            }
        }
        return instance;
    }

    protected CrimeContext() {
        super();
    }

}
