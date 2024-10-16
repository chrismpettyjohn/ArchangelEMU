package com.us.archangel.corp.context;

import com.us.archangel.core.GenericContext;
import com.us.archangel.corp.model.CorpRoleModel;

public class CorpRoleContext extends GenericContext<CorpRoleModel> {

    private static volatile CorpRoleContext instance;

    public static CorpRoleContext getInstance() {
        if (instance == null) {
            synchronized (CorpRoleContext.class) {
                if (instance == null) {
                    instance = new CorpRoleContext();
                }
            }
        }
        return instance;
    }

    protected CorpRoleContext() {
        super();
    }

    public CorpRoleModel findByCorpAndOrderId(int corpId, int orderId) {
        for (CorpRoleModel model : this.getAll().values()) {
            if (model.getCorpId() == corpId && model.getOrderId() == orderId) {
                return model;
            }
        }
        return null;
    }
}
