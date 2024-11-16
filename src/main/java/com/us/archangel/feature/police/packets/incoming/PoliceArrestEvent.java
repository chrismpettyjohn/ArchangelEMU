package com.us.archangel.feature.police.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.bounty.model.BountyModel;
import com.us.archangel.bounty.service.BountyService;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.corp.model.CorpModel;
import com.us.archangel.crime.model.CrimeModel;
import com.us.archangel.crime.service.CrimeService;
import com.us.archangel.feature.police.actions.ServeJailTimeAction;
import com.us.archangel.feature.police.packets.outgoing.UserArrestedComposer;
import com.us.archangel.player.enums.PlayerAction;

import java.util.Collection;
import java.util.List;

public class PoliceArrestEvent extends MessageHandler {
    @Override
    public void handle() {

        if (!this.client.getHabbo().getPlayer().canInteract()) {
            return;
        }

        CorpModel corp = this.client.getHabbo().getPlayer().getCorp();

        if (corp == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.unemployed"));
            return;
        }

        if (corp.getIndustry() != CorpIndustry.Police) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.police_only"));
            return;
        }

        if (!this.client.getHabbo().getPlayer().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.must_be_working"));
            return;
        }

        Habbo targetedHabbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(this.packet.readInt());

        if (targetedHabbo.getPlayer().getCurrentAction() != PlayerAction.Cuffed) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_arrest_must_be_cuffed").replace(":username", targetedHabbo.getHabboInfo().getUsername()));
            return;
        }

        if (this.client.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId() != corp.getRoomId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.cmd_arrest_must_be_in_station"));
            return;
        }

        int distanceX = targetedHabbo.getRoomUnit().getCurrentPosition().getX() - this.client.getHabbo().getRoomUnit().getCurrentPosition().getX();
        int distanceY = targetedHabbo.getRoomUnit().getCurrentPosition().getY() - this.client.getHabbo().getRoomUnit().getCurrentPosition().getY();

        int rangeInTiles = 1;

        boolean isTargetWithinRange = Math.abs(distanceX) <= rangeInTiles && Math.abs(distanceY) <= rangeInTiles;

        if (!isTargetWithinRange) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.generic_target_too_far").replace(":username", targetedHabbo.getHabboInfo().getUsername()));
            return;
        }

        this.client.getHabbo().getPlayer().setEscortingPlayerId(null);
        targetedHabbo.getPlayer().setCurrentAction(PlayerAction.None);

        List<BountyModel> pendingBounties = BountyService.getInstance().findPendingByUserId(targetedHabbo.getHabboInfo().getId());

        int totalJailTimeSeconds = pendingBounties.stream()
                .mapToInt(bounty -> {
                    CrimeModel crimeInst = CrimeService.getInstance().getById(bounty.getCrimeId());
                    return crimeInst.getJailTimeSeconds();
                })
                .sum();

        Collection<Habbo> onlineHabbos = Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values();

        for (Habbo onlineHabbo : onlineHabbos) {
            onlineHabbo.getClient().sendResponse(new UserArrestedComposer(targetedHabbo, this.client.getHabbo()));
        }

        Emulator.getThreading().run(new ServeJailTimeAction(targetedHabbo, totalJailTimeSeconds));
    }
}