package com.us.archangel.feature.corp.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.roleplay.commands.corp.CorpStartWorkCommand;
import com.us.roleplay.corp.Corp;
import com.us.roleplay.corp.CorpManager;
import com.us.roleplay.corp.CorpPosition;
import com.us.roleplay.database.CorpPositionRepository;
import com.us.archangel.feature.corp.packets.outgoing.CorpEmployeeListComposer;
import com.us.archangel.feature.corp.packets.outgoing.CorpPositionListComposer;

public class CorpEditPositionEvent extends MessageHandler {

    @Override
    public void handle() {
        int corpID = this.packet.readInt();
        int corpPositionID = this.packet.readInt();

        Corp corp = CorpManager.getInstance().getCorpByID(corpID);

        if (corp == null) {
            return;
        }

        if (corp.getGuild().getOwnerId() != this.client.getHabbo().getHabboInfo().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.cor.not_the_owner"));
            return;
        }

        CorpPosition corpPosition = corp.getPositionByID(corpPositionID);

        if (corpPosition == null) {
            return;
        }

        corpPosition.setName(this.packet.readString());
        corpPosition.setMotto(this.packet.readString());
        corpPosition.setSalary(this.packet.readInt());
        corpPosition.setMaleFigure(this.packet.readString());
        corpPosition.setFemaleFigure(this.packet.readString());
        corpPosition.setCanHire((this.packet.readBoolean()));
        corpPosition.setCanFire((this.packet.readBoolean()));
        corpPosition.setCanPromote((this.packet.readBoolean()));
        corpPosition.setCanDemote((this.packet.readBoolean()));
        corpPosition.setCanWorkAnywhere((this.packet.readBoolean()));

        CorpPositionRepository.getInstance().upsertCorpPosition(corpPosition);
        corp.addPosition(corpPosition);

        this.client.getHabbo().whisper(Emulator.getTexts()
                .getValue("roleplay.corp_position.edit_success")
                .replace(":position", corpPosition.getName())
        );

        this.client.sendResponse(new CorpPositionListComposer(corp));
    }

    public static class CorpEmployeeListEvent extends MessageHandler {

        @Override
        public void handle() {
            int corpID = this.packet.readInt();

            Corp corp = CorpManager.getInstance().getCorpByID(corpID);

            if (corp == null) {
                return;
            }

            this.client.sendResponse(new CorpEmployeeListComposer(corp.getGuild().getId()));
        }
    }

    public static class CorpStartWorkEvent extends MessageHandler {
        @Override
        public void handle() {
            new CorpStartWorkCommand().handle(this.client, new String[] {});
        }
    }
}