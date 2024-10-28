package com.us.nova.user.context;

import com.us.nova.core.GenericContext;
import com.us.nova.user.model.UserModel;

public class UserContext extends GenericContext<UserModel> {

    private static volatile UserContext instance;

    public static UserContext getInstance() {
        if (instance == null) {
            synchronized (UserContext.class) {
                if (instance == null) {
                    instance = new UserContext();
                }
            }
        }
        return instance;
    }

    protected UserContext() {
        super();
    }

}
