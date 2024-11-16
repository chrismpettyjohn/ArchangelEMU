package com.us.nova.user.context;

import com.us.nova.core.GenericContext;
import com.us.nova.user.model.UserGuestbookModel;

public class UserGuestbookContext extends GenericContext<UserGuestbookModel> {

    private static volatile UserGuestbookContext instance;

    public static UserGuestbookContext getInstance() {
        if (instance == null) {
            synchronized (UserGuestbookContext.class) {
                if (instance == null) {
                    instance = new UserGuestbookContext();
                }
            }
        }
        return instance;
    }

    protected UserGuestbookContext() {
        super(UserGuestbookModel.class);
    }

}
