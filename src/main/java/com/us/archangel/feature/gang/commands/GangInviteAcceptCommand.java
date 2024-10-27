package com.us.archangel.feature.gang.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.guilds.Guild;
import com.us.archangel.gang.model.GangInviteModel;
import com.us.archangel.gang.service.GangInviteService;

public class GangInviteAcceptCommand extends Command {
    public GangInviteAcceptCommand() {
        super("cmd_gang_invite_accept");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params == null) {
            return true;
        }

        String gangName = params[1];

        if (gangName == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.gang_not_found"));
            return true;
        }

        Guild targetedGang = Emulator.getGameEnvironment().getGuildManager().getGuild(gangName);

        if (targetedGang == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.gang_not_found"));
            return true;
        }

        GangInviteModel gangInvite = GangInviteService.getInstance().getByGangAndUserId(targetedGang.getId(), gameClient.getHabbo().getHabboInfo().getId());

        if (gangInvite == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_invite_missing"));
            return true;
        }


        gameClient.getHabbo().getPlayer().setGangId(targetedGang.getId());
        gameClient.getHabbo().getPlayer().setGangRoleId(gangInvite.getGangRoleId());


        GangInviteService.getInstance().deleteById(gangInvite.getId());

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_invite_accepted").replace(":gang", targetedGang.getName()));

        return true;
    }
}