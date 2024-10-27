package com.us.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.player.enums.PlayerAction;

public class RoleplayHelper {

    private static RoleplayHelper instance;

    public static RoleplayHelper getInstance() {
        if (instance == null) {
            instance = new RoleplayHelper();
        }
        return instance;
    }

    public Habbo getTarget(GameClient gameClient, String[] params) {
        if (params == null || params.length == 0) {
            return null;
        }

        String targetedUsername = params[1];

        if (gameClient.getHabbo().getPlayer().getCurrentAction() == PlayerAction.Cuffed || gameClient.getHabbo().getPlayer().getCurrentAction() == PlayerAction.Stunned || gameClient.getHabbo().getPlayer().getCurrentAction() == PlayerAction.Escorted || gameClient.getHabbo().getPlayer().isDead()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.generic.not_allowed"));
            return null;
        }

        if (targetedUsername == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return null;
        }

        Habbo targetedHabbo = gameClient.getHabbo().getRoomUnit().getRoom().getRoomUnitManager().getRoomHabboByUsername(targetedUsername);

        if (targetedHabbo == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found").replace("%username%", targetedUsername));
            return null;
        }

        if (targetedHabbo == gameClient.getHabbo()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_hit_user_is_self"));
            return null;
        }

        if (targetedHabbo.getPlayer().isDead()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.target_dead").replace(":username", targetedUsername));
            return null;
        }

        return targetedHabbo;
    }



}
