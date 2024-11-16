package com.us.archangel.feature.police.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.bounty.service.BountyService;
import com.us.archangel.corp.enums.CorpIndustry;
import com.us.archangel.feature.police.packets.outgoing.WantedListQueryListComposer;

public class WantedListDeleteEvent extends MessageHandler {

    @Override
    public void handle() {
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

        BountyService.getInstance().deleteById(this.packet.readInt());

        this.client.sendResponse(new WantedListQueryListComposer());
    }


}
