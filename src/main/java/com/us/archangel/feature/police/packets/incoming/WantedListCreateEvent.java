package com.us.archangel.feature.police.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.bounty.model.BountyModel;
import com.us.archangel.bounty.service.BountyService;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.crime.model.CrimeModel;
import com.us.archangel.crime.service.CrimeService;
import com.us.archangel.feature.police.packets.outgoing.WantedListQueryListComposer;
import com.us.roleplay.users.HabboRoleplayHelper;

import java.util.List;

public class WantedListCreateEvent extends MessageHandler {
    @Override
    public void handle() {
        Habbo targetedHabbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(this.packet.readString());

        if (targetedHabbo == null) {
            return;
        }

        int crimeID = this.packet.readInt();

        CrimeModel crime = CrimeService.getInstance().getById(crimeID);

        if (crime == null) {
            return;
        }

        if (this.client.getHabbo().getPlayer().getCorp() == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.unemployed"));
            return;
        }

        if (this.client.getHabbo().getPlayer().getCorp().getIndustry() != CorpIndustry.Police) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.police_only"));
            return;
        }

        if (!this.client.getHabbo().getPlayer().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.roleplay.must_be_working"));
            return;
        }

        BountyModel bounty = new BountyModel();
        bounty.setUserId(targetedHabbo.getHabboInfo().getId());
        bounty.setCrimeId(crimeID);
        BountyService.getInstance().create(bounty);

        List<Habbo> policeOnline = HabboRoleplayHelper.getUsersByCorpIndustry(CorpIndustry.Police);
        List<Habbo> policeWorking = HabboRoleplayHelper.getUsersWorking(policeOnline);

        for (Habbo policeOfficer : policeWorking) {
            policeOfficer.getClient().sendResponse(new WantedListQueryListComposer());
        }

        this.client.getHabbo().shout(Emulator.getTexts().getValue("roleplay.police.wanted_list_changed"));

        this.client.sendResponse(new WantedListQueryListComposer());
    }
}