package com.us.archangel.bounty.context;

import com.us.archangel.bounty.model.BountyModel;
import com.us.archangel.core.GenericContext;


public class BountyContext extends GenericContext<BountyModel> {

    private static BountyContext instance;

    private BountyContext() {
        super();
    }

    public static BountyContext getInstance() {
        if (instance == null) {
            instance = new BountyContext();
        }
        return instance;
    }
}
