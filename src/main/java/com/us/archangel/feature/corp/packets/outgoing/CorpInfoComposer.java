package com.us.archangel.feature.corp.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.service.CorpService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CorpInfoComposer extends MessageComposer {
    private final int corpID;

    @Override
    protected ServerMessage composeInternal() {
        CorpModel matchingCorp = CorpService.getInstance().getById(this.corpID);
        this.response.init(Outgoing.corpInfoComposer);
        this.response.appendInt(matchingCorp.getId());;
        this.response.appendInt(matchingCorp.getUserId());;
        this.response.appendInt(matchingCorp.getRoomId());
        this.response.appendString(matchingCorp.getDisplayName());
        this.response.appendString(matchingCorp.getDescription());
        this.response.appendString(matchingCorp.getBadge());
        this.response.appendString(matchingCorp.getIndustry().toString());
        return this.response;
    }
}
