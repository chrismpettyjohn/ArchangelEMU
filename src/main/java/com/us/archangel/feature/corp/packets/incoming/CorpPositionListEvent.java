package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.corp.Corp;
import com.us.roleplay.corp.CorpManager;
import com.us.archangel.feature.corp.packets.outgoing.CorpPositionListComposer;

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