package com.eu.archangel.corp.context;

import com.eu.archangel.core.GenericContext;
import com.eu.archangel.corp.model.CorpRoleModel;

public class CorpRoleContext extends GenericContext<CorpRoleModel> {

    private static CorpRoleContext instance;

    private CorpRoleContext() {
        super();
    }

    public static CorpRoleContext getInstance() {
        if (instance == null) {
            instance = new CorpRoleContext();
        }
        return instance;
    }
}
