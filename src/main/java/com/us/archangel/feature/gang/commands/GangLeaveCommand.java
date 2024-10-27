package com.us.archangel.feature.gang.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;

public class GangLeaveCommand extends Command {
    public GangLeaveCommand() {
        super("cmd_gang_leave");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (gameClient.getHabbo().getPlayer().getGang() == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.not_in_a_gang"));
            return true;
        }


        if (gameClient.getHabbo().getPlayer().getGang().getUserId() == gameClient.getHabbo().getPlayer().getUserId()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_leave_cant_is_owner"));
            return true;
        }

        gameClient.getHabbo().getPlayer().setGangId(null);
        gameClient.getHabbo().getPlayer().setGangRoleId(null);

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_leave_success"));

        return true;
    }
}