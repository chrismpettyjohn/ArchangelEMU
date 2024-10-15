package com.us.archangel.penalty.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.penalty.model.PenaltyModel;

public class PenaltyContext extends GenericContext<PenaltyModel> {

    private static PenaltyContext instance;

    private PenaltyContext() {
        super();
    }

    public static PenaltyContext getInstance() {
        if (instance == null) {
            instance = new PenaltyContext();
        }
        return instance;
    }
}
