package com.us.nova.betacode.context;

import com.us.nova.betacode.model.BetaCodeModel;
import com.us.nova.core.GenericContext;

public class BetaCodeContext extends GenericContext<BetaCodeModel> {

    private static volatile BetaCodeContext instance;

    public static BetaCodeContext getInstance() {
        if (instance == null) {
            synchronized (BetaCodeContext.class) {
                if (instance == null) {
                    instance = new BetaCodeContext();
                }
            }
        }
        return instance;
    }

    protected BetaCodeContext() {
        super();
    }

}
