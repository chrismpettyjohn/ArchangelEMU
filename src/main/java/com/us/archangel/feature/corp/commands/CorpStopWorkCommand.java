package com.us.archangel.feature.corp.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;

public class CorpStopWorkCommand extends Command {
    public CorpStopWorkCommand() {
        super("cmd_corp_stopwork");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (!gameClient.getHabbo().getPlayer().isWorking()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_stop_work_no_shift"));
            return true;
        }


        gameClient.getHabbo().getPlayer().setWorkTimeRemainingSecs(0);
        gameClient.getHabbo().getInventory().getStoreShiftComponent().clearProducts();
        gameClient.getHabbo().getRoomUnit().getRoom().sendComposer(new UserRoleplayStatsChangeComposer(gameClient.getHabbo()).compose());
        return true;
    }
}