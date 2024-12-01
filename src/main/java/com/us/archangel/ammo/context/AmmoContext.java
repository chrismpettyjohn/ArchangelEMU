package com.us.archangel.ammo.context;

import com.us.archangel.ammo.model.AmmoModel;
import com.us.nova.core.GenericContext;

public class AmmoContext extends GenericContext<AmmoModel> {

    private static volatile AmmoContext instance;

    public static AmmoContext getInstance() {
        if (instance == null) {
            synchronized (AmmoContext.class) {
                if (instance == null) {
                    instance = new AmmoContext();
                }
            }
        }
        return instance;
    }

    protected AmmoContext() {
        super(AmmoModel.class);
    }
}
