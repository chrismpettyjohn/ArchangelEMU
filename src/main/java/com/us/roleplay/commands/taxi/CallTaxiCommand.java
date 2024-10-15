package com.us.roleplay.commands.taxi;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.us.roleplay.actions.CallTaxiAction;

public class CallTaxiCommand extends Command {
    public CallTaxiCommand() {
        super("cmd_taxi");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        if (params == null) {
            return false;
        }

        if (params[1] == null) {
            return true;
        }

        int roomID = Integer.parseInt(params[1]);

        if (gameClient.getHabbo().getRoomUnit().getRoom() != null && gameClient.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId() == roomID ) {
            return true;
        }

        Room targetedRoom = Emulator.getGameEnvironment().getRoomManager().getRoom(roomID);

        if (targetedRoom == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.taxi.not_found"));
            return true;
        }

        Emulator.getThreading().run(new CallTaxiAction(gameClient.getHabbo(), targetedRoom));

        return true;
    }
}