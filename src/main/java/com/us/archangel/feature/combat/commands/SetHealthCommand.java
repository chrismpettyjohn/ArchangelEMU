package com.us.archangel.feature.combat.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.nova.core.NotificationHelper;

public class SetHealthCommand extends Command {
    public SetHealthCommand() {
        super("cmd_sethealth");
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        String targetedUsername = params[1];

        if (targetedUsername == null) {
            return true;
        }

        Habbo targetedHabbo =Emulator.getGameEnvironment().getHabboManager().getHabbo(targetedUsername);

        if (targetedHabbo == null) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found").replace(":username", targetedUsername));
            return true;
        }

        int updatedHealth = Integer.parseInt(params[2]);

        targetedHabbo.getPlayer().setHealthNow(updatedHealth);
        targetedHabbo.getPlayer().setHealthMax(updatedHealth);

        String healthGivenMessage = Emulator.getTexts().getValue("roleplay.set_health_gave")
                .replace(":username", targetedUsername)
                .replace(":healthRestored", Integer.toString(updatedHealth));

        gameClient.getHabbo().shout(healthGivenMessage);

        targetedHabbo.shout(Emulator.getTexts()
                .getValue("commands.roleplay.user_health_remaining")
                .replace(":currentHealth", Integer.toString(targetedHabbo.getPlayer().getHealthNow()))
                .replace(":maximumHealth", Integer.toString(targetedHabbo.getPlayer().getHealthMax()))
        );

        NotificationHelper.sendRoom(targetedHabbo.getRoomUnit().getRoom().getRoomInfo().getId(), new UserRoleplayStatsChangeComposer(targetedHabbo));

        return true;
    }

}
