package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

public class MuteBotsCommand extends Command {
    public MuteBotsCommand() {
        super(null, Emulator.getTexts().getValue("commands.keys.cmd_mute_bots").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        gameClient.getHabbo().getHabboStats().setIgnoreBots(!gameClient.getHabbo().getHabboStats().isIgnoreBots());
        gameClient.getHabbo().whisper(getTextsValue("commands.succes.cmd_mute_bots." + (gameClient.getHabbo().getHabboStats().isIgnoreBots() ? "ignored" : "unignored")), RoomChatMessageBubbles.ALERT);
        return true;
    }
}