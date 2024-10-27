package com.us.archangel.feature.gang.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.us.archangel.gang.model.GangRoleModel;
import com.us.archangel.gang.service.GangRoleService;
import com.us.archangel.gang.service.GangService;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.service.PlayerService;

import java.util.List;

public class GangDisbandCommand extends Command {
    public GangDisbandCommand() {
        super("cmd_gang_disband");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (gameClient.getHabbo().getPlayer().getGang() == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.not_in_a_gang"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getGang() == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_disband_not_allowed"));
            return true;
        }

        if (gameClient.getHabbo().getPlayer().getGang().getUserId() != gameClient.getHabbo().getPlayer().getUserId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_disband_not_allowed"));
            return true;
        }

        List<PlayerModel> players = PlayerService.getInstance().getByCorpId(gameClient.getHabbo().getPlayer().getCorpId());

        for (PlayerModel player : players) {
            player.setGangRoleId(null);
            player.setGangId(null);
        }

        List<GangRoleModel> gangRoles = GangRoleService.getInstance().getAll();

        for (GangRoleModel gangRole : gangRoles) {
            GangRoleService.getInstance().deleteById(gangRole.getId());
        }

        GangService.getInstance().deleteById(gameClient.getHabbo().getPlayer().getGang().getId());

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_disband_success"));

        return true;
    }
}