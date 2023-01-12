package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

public class UpdateYoutubePlaylistsCommand extends Command {
    public UpdateYoutubePlaylistsCommand() {
        super("cmd_update_youtube_playlists", Emulator.getTexts().getValue("commands.keys.cmd_update_youtube_playlists").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        Emulator.getGameEnvironment().getItemManager().getYoutubeManager().load();

        gameClient.getHabbo().whisper(getTextsValue("commands.succes.cmd_update_youtube_playlists"), RoomChatMessageBubbles.ALERT);

        return true;
    }
}
