package com.us.roleplay.messages.incoming.corp;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.corp.Corp;
import com.us.roleplay.corp.CorpManager;
import com.us.roleplay.messages.outgoing.corp.CorpPositionListComposer;

public class CorpPositionListEvent extends MessageHandler {

    @Override
    public void handle() {
        int corpID = this.packet.readInt();

        Corp corp = CorpManager.getInstance().getCorpByID(corpID);

        if (corp == null) {
            return;
        }

        this.client.sendResponse(new CorpPositionListComposer(corp));
    }
}