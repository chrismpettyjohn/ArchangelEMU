package com.us.archangel.bounty.context;

import com.us.archangel.bounty.model.BountyModel;
import com.us.archangel.core.GenericContext;


public class BountyContext extends GenericContext<BountyModel> {

    private static volatile BountyContext instance;

    public static BountyContext getInstance() {
        if (instance == null) {
            synchronized (BountyContext.class) {
                if (instance == null) {
                    instance = new BountyContext();
                }
            }
        }
        return instance;
    }

    protected BountyContext() {
        super();
    }

}
