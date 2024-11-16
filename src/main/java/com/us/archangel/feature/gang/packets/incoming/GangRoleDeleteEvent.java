package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangQueryListComposer;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.service.GangRoleService;
import com.us.archangel.gang.service.GangService;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;
import java.util.List;

public class GangRoleDeleteEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canEditAllGangs = this.client.getHabbo().hasPermissionRight(Permission.ACC_GANGS_EDIT_ALL);

        GangRoleModel gangRole = GangRoleService.getInstance().getById(this.packet.readInt());
        GangModel gang = GangService.getInstance().getById(gangRole.getGangId());

        if (gang.getUserId() != this.client.getHabbo().getHabboInfo().getId() && !canEditAllGangs) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        List<GangRoleModel> gangRoleModels = GangRoleService.getInstance().findManyByGangId(gang.getId());

        if (gangRoleModels.size() == 1) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.cant_delete_last_role"));
            return;
        }

        List<PlayerModel> players = PlayerService.getInstance().getByGangRoleId(gangRole.getId());

        for (PlayerModel player : players) {
            player.setGangId(null);
            player.setGangRoleId(null);
            player.save();
        }

        GangRoleService.getInstance().deleteById(gang.getId());

        this.client.sendResponse(new GangQueryListComposer());
    }
}