package com.us.archangel.corp.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.corp.model.CorpModel;

public class CorpContext extends GenericContext<CorpModel> {

    private static CorpContext instance;

    private CorpContext() {
        super();
    }

    public static CorpContext getInstance() {
        if (instance == null) {
            instance = new CorpContext();
        }
        return instance;
    }
}
