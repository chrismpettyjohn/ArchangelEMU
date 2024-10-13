package com.eu.archangel.corp.context;

import com.eu.archangel.core.GenericContext;
import com.eu.archangel.corp.model.CorpModel;

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
