package com.us.archangel.bounty.context;

import com.us.archangel.bounty.model.BountyModel;
import com.us.nova.core.GenericContext;

import java.util.List;
import java.util.stream.Collectors;

public class BountyContext extends GenericContext<BountyModel> {

    private static volatile BountyContext instance;

    public static synchronized BountyContext getInstance() {
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
        super(BountyModel.class);
    }
    public List<BountyModel> getByUserId(int userId) {
        return getAll().values().stream()
                .filter(model -> model.getUserId() == userId)
                .collect(Collectors.toList());
    }

}
