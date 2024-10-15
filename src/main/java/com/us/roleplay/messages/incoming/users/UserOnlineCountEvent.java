package com.us.roleplay.messages.incoming.users;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.messages.outgoing.user.UserOnlineCountComposer;

public class UserOnlineCountEvent extends MessageHandler {
    @Override
    public void handle() {
        this.client.sendResponse(new UserOnlineCountComposer());
    }
}