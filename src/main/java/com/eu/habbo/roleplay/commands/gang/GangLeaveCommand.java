package com.eu.habbo.roleplay.commands.gang;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.guilds.GuildRank;

public class GangLeaveCommand extends Command {
    public GangLeaveCommand() {
        super("cmd_gang_leave");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (gameClient.getHabbo().getHabboRoleplayStats().getGang() == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.not_in_a_gang"));
            return true;
        }

        if (gameClient.getHabbo().getHabboRoleplayStats().getGangPosition() == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_leave_cant_is_owner"));
            return true;
        }

        if (gameClient.getHabbo().getHabboRoleplayStats().getGangPosition().getRank() == GuildRank.OWNER) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_leave_cant_is_owner"));
            return true;
        }

        gameClient.getHabbo().getHabboRoleplayStats().setGang(null);

        gameClient.getHabbo().shout(Emulator.getTexts().getValue("commands.roleplay.cmd_gang_leave_success"));

        return true;
    }
}