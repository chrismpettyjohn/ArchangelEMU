package com.us.nova.bugreport.context;

import com.us.nova.bugreport.model.BugReportModel;
import com.us.nova.core.GenericContext;

public class BugReportContext extends GenericContext<BugReportModel> {

    private static volatile BugReportContext instance;

    public static BugReportContext getInstance() {
        if (instance == null) {
            synchronized (BugReportContext.class) {
                if (instance == null) {
                    instance = new BugReportContext();
                }
            }
        }
        return instance;
    }

    protected BugReportContext() {
        super();
    }

}
