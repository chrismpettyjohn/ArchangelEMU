package com.us.roleplay.messages.incoming.corp;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.corp.Corp;
import com.us.roleplay.corp.CorpManager;
import com.us.roleplay.messages.outgoing.corp.CorpEmployeeListComposer;

public class CorpEmployeeListEvent extends MessageHandler {

    @Override
    public void handle() {
        int corpID = this.packet.readInt();

        Corp corp = CorpManager.getInstance().getCorpByID(corpID);

        if (corp == null) {
            return;
        }

        this.client.sendResponse(new CorpEmployeeListComposer(corp.getGuild().getId()));
    }
}