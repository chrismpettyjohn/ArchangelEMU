package com.us.archangel.feature.gang.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.service.GangRoleService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GangRoleQueryListComposer extends MessageComposer {
    private final int gangId;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.gangRoleQueryListComposer);

        List<GangRoleModel> gangRoles = GangRoleService.getInstance().getByGangId(this.gangId);

        this.response.appendInt(gangRoles.size());

        for (GangRoleModel gang : gangRoles) {
            this.response.appendString(String.format("%s;%s;%s;%s;%s", gang.getId(), gang.getOrderId(), gang.getName(), gang.isCanInvite(), gang.isCanKick()));
        }

        return this.response;
    }

}
