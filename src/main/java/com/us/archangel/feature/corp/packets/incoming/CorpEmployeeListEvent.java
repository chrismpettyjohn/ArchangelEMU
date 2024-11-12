package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.corp.packets.outgoing.CorpEmployeeListComposer;

public class CorpEmployeeListEvent extends MessageHandler {

    @Override
    public void handle() {
        int corpID = this.packet.readInt();

        CorpModel corp = CorpService.getInstance().getById(corpID);

        if (corp == null) {
            return;
        }

        this.client.sendResponse(new CorpEmployeeListComposer(corp.getId()));
    }
}