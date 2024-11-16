package com.us.archangel.feature.corp.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class CorpPositionListComposer extends MessageComposer {
    private final CorpModel corp;

    @Override
    protected ServerMessage composeInternal() {
        List<CorpRoleModel> corpPositions = CorpRoleService.getInstance().findManyByCorpId(this.corp.getId());
        List<PlayerModel> playerModels = PlayerService.getInstance().getByCorpId(this.corp.getId());

        this.response.init(Outgoing.corpPositionListComposer);
        this.response.appendInt(corp.getId());
        this.response.appendInt(corpPositions.size());

        corpPositions.sort(Comparator.comparingInt(CorpRoleModel::getOrderId));

        for (CorpRoleModel corpPosition : corpPositions) {
            int totalMembersInPosition = (int) playerModels
                    .stream()
                    .filter(player -> player.getCorpRoleId() == corpPosition.getId())
                    .count();
            this.response.appendString(
                    corpPosition.getId() + ";"
                            + corpPosition.getDisplayName() + ";"
                            + corpPosition.getSalary() + ";"
                            + corpPosition.getMaleFigure() + ";"
                            + corpPosition.getFemaleFigure() + ";"
                            + totalMembersInPosition
            );
        }

        return this.response;
    }
}
