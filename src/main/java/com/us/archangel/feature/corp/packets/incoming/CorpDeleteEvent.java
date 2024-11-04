package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.corp.model.CorpPermissions;
import com.us.archangel.corp.model.CorpRoleModel;
import com.us.archangel.corp.service.CorpRoleService;
import com.us.archangel.corp.service.CorpService;
import com.us.archangel.feature.corp.packets.outgoing.CorpListComposer;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;

import java.util.List;

public class CorpDeleteEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canDeleteCorps = this.client.getHabbo().hasPermissionRight(CorpPermissions.DELETE);

        if (!canDeleteCorps) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        int corpId = this.packet.readInt();

        CorpModel corpModel = CorpService.getInstance().getById(corpId);

        // identify welfare corp and role
        CorpModel welfareCorp = CorpService.getInstance().findManyByIndustry(CorpIndustry.PublicAid).get(0);
        CorpRoleModel welfareRole = CorpRoleService.getInstance().getByCorpAndOrderId(welfareCorp.getId(), 1);

        // Reassign users
        List<PlayerModel> playerModels = PlayerService.getInstance().getByCorpId(corpId);

        for (PlayerModel playerModel : playerModels) {
            playerModel.setCorpId(welfareRole.getCorpId());
            playerModel.setCorpRoleId(welfareRole.getId());
            PlayerService.getInstance().update(playerModel.getUserId(), playerModel);
        }

        // Delete roles
        List<CorpRoleModel> corpRoleModels = CorpRoleService.getInstance().findManyByCorpId(corpId);
        for (CorpRoleModel corpRole : corpRoleModels) {
            CorpRoleService.getInstance().deleteById(corpRole.getId());
        }

        // Unassign from room?
        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(corpModel.getRoomId());
        room.getRoomInfo().setGuild(null);
        room.setNeedsUpdate(true);

        CorpService.getInstance().deleteById(corpId);

        this.client.sendResponse(new CorpListComposer());
    }
}
