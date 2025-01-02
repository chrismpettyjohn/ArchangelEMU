package com.us.archangel.feature.combat.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;

public class TogglePassiveModeEvent extends MessageHandler {
    @Override
    public void handle() {
        boolean passiveMode = this.packet.readBoolean();
        this.client.getHabbo().getPlayer().setPassiveMode(passiveMode);
        this.client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.passive_mode_toggle").replace(":state", passiveMode ? "on" : "off"));
        this.client.sendResponse(new UserRoleplayStatsChangeComposer(this.client.getHabbo()));
    }
}