package com.us.archangel.corp.context;

import com.us.nova.core.GenericContext;
import com.us.archangel.corp.model.CorpModel;

public class CorpContext extends GenericContext<CorpModel> {

    private static volatile CorpContext instance;

    public static CorpContext getInstance() {
        if (instance == null) {
            synchronized (CorpContext.class) {
                if (instance == null) {
                    instance = new CorpContext();
                }
            }
        }
        return instance;
    }

    protected CorpContext() {
        super(CorpModel.class);
    }
}
