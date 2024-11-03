package com.us.archangel.feature.gang.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.gang.entity.GangInviteEntity;
import com.us.archangel.gang.mapper.GangInviteMapper;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.service.GangInviteService;
import com.us.archangel.gang.service.GangRoleService;

public class GangInviteUserCommand extends Command {
    public GangInviteUserCommand() {
        super("cmd_gang_invite");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params == null) {
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getGang() == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_invite_not_in_a_gang"));
            return true;
        }

        String targetedUsername = params[1];

        if (targetedUsername == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return true;
        }

        Habbo targetedHabbo = gameClient.getHabbo().getRoomUnit().getRoom().getRoomUnitManager().getRoomHabboByUsername(targetedUsername);

        if (targetedHabbo == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return true;
        }

        if (targetedHabbo.getPlayer().getGang() != null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_invite_user_already_in_gang"));
            return true;
        }

        GangRoleModel gangRole = GangRoleService.getInstance().getByGangIdAndOrderId(gameClient.getHabbo().getPlayer().getGangId(), 1);

        GangInviteEntity gangInvite = new GangInviteEntity();
        gangInvite.setGangId(gameClient.getHabbo().getPlayer().getGangId());
        gangInvite.setGangRoleId(gangRole.getId());

        GangInviteService.getInstance().create(GangInviteMapper.toModel(gangInvite));

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_invite_sent").replace(":username", targetedHabbo.getHabboInfo().getUsername()));

        targetedHabbo.whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_invite_received").replace(":gang",gameClient.getHabbo().getPlayer().getGang().getDisplayName()));

        return true;
    }
}