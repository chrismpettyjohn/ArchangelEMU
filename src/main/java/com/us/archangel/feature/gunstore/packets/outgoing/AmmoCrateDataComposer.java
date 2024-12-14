package com.us.archangel.feature.gunstore.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.service.AmmoService;

import java.util.List;


public class AmmoCrateDataComposer extends MessageComposer {

    @Override
    protected ServerMessage composeInternal() {

        List<AmmoModel> ammoModels = AmmoService.getInstance().getAll();

        this.response.init(Outgoing.ammoCrateDataComposer);

        this.response.appendInt(ammoModels.size());

        for (AmmoModel ammoModel : ammoModels) {
            this.response.appendString(
                    ammoModel.getId() + ";" +
                            ammoModel.getUniqueName() + ";" +
                            ammoModel.getDisplayName() + ";" +
                            ammoModel.getType() + ";" +
                            ammoModel.getSize()
            );
        }

        return this.response;
    }
}
