package com.us.nova.user.context;

import com.us.nova.core.GenericContext;
import com.us.nova.user.model.UserSSOModel;

public class UserSSOContext extends GenericContext<UserSSOModel> {

    private static volatile UserSSOContext instance;

    public static UserSSOContext getInstance() {
        if (instance == null) {
            synchronized (UserSSOContext.class) {
                if (instance == null) {
                    instance = new UserSSOContext();
                }
            }
        }
        return instance;
    }

    protected UserSSOContext() {
        super(UserSSOModel.class);
    }

}
