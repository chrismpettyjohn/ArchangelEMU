package com.us.archangel.feature.gang.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.gang.packets.outgoing.GangMemberQueryListComposer;
import com.us.archangel.gang.model.GangModel;
import com.us.archangel.gang.service.GangService;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;

public class GangMemberKickEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean canEditAllGangs = this.client.getHabbo().hasPermissionRight(Permission.ACC_GANGS_EDIT_ALL);

        int gangId = this.packet.readInt();
        String username = this.packet.readString();

        if (username == null) {
            return;
        }

        GangModel gang = GangService.getInstance().getById(gangId);

        if (gang.getUserId() != this.client.getHabbo().getHabboInfo().getId() && !canEditAllGangs) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        HabboInfo habbo = Emulator.getGameEnvironment().getHabboManager().getOfflineHabboInfo(username);
        PlayerModel player = PlayerService.getInstance().getById(habbo.getId());

        if (player.getGangId() != gangId && !canEditAllGangs) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("nova.generic.not_allowed"));
            return;
        }

        player.setGangId(null);
        player.setGangRoleId(null);
        player.save();

        this.client.sendResponse(new GangMemberQueryListComposer(gangId));
    }
}