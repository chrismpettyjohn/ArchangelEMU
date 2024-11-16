package com.us.archangel.police.context;

import com.us.archangel.police.model.PoliceWarrantModel;
import com.us.nova.core.GenericContext;

public class PoliceWarrantContext extends GenericContext<PoliceWarrantModel> {

    private static volatile PoliceWarrantContext instance;

    public static synchronized PoliceWarrantContext getInstance() {
        if (instance == null) {
            instance = new PoliceWarrantContext();
        }
        return instance;
    }

    protected PoliceWarrantContext() {
        super(PoliceWarrantModel.class);
    }
}
