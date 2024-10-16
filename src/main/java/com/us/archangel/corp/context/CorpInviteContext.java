package com.us.archangel.corp.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.corp.model.CorpInviteModel;

public class CorpInviteContext extends GenericContext<CorpInviteModel> {

    private static volatile CorpInviteContext instance;

    public static CorpInviteContext getInstance() {
        if (instance == null) {
            synchronized (CorpInviteContext.class) {
                if (instance == null) {
                    instance = new CorpInviteContext();
                }
            }
        }
        return instance;
    }

    protected CorpInviteContext() {
        super();
    }
}
