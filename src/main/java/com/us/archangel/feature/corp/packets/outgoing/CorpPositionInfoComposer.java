package com.us.archangel.feature.corp.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.roleplay.corp.Corp;
import com.us.roleplay.corp.CorpManager;
import com.us.roleplay.corp.CorpPosition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CorpPositionInfoComposer extends MessageComposer {
    private final int corpID;
    private final int corpPositionID;

    @Override
    protected ServerMessage composeInternal() {
        Corp matchingCorp = CorpManager.getInstance().getCorpByID(this.corpID);
        CorpPosition matchingPosition = matchingCorp.getPositionByID(this.corpPositionID);
        this.response.init(Outgoing.corpPositionInfoComposer);
        this.response.appendInt(matchingPosition.getId());
        this.response.appendInt(matchingPosition.getCorporationID());
        this.response.appendString(matchingPosition.getName());
        this.response.appendString(matchingPosition.getMotto());
        this.response.appendInt(matchingPosition.getSalary());
        this.response.appendInt(matchingPosition.getOrderId());
        this.response.appendString(matchingPosition.getMaleFigure());
        this.response.appendString(matchingPosition.getFemaleFigure());
        this.response.appendBoolean(matchingPosition.isCanHire());
        this.response.appendBoolean(matchingPosition.isCanFire());
        this.response.appendBoolean(matchingPosition.isCanPromote());
        this.response.appendBoolean(matchingPosition.isCanDemote());
        this.response.appendBoolean(matchingPosition.isCanWorkAnywhere());
        return this.response;
    }
}
