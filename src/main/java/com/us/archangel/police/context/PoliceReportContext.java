package com.us.archangel.police.context;

import com.us.archangel.police.model.PoliceReportModel;
import com.us.nova.core.GenericContext;

public class PoliceReportContext extends GenericContext<PoliceReportModel> {

    private static volatile PoliceReportContext instance;

    public static synchronized PoliceReportContext getInstance() {
        if (instance == null) {
            instance = new PoliceReportContext();
        }
        return instance;
    }

    protected PoliceReportContext() {
        super(PoliceReportModel.class);
    }
}
