package com.us.archangel.feature.gunstore.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;

import java.util.List;

public class WeaponVendingMachineDataComposer extends MessageComposer {

    @Override
    protected ServerMessage composeInternal() {
        List<WeaponModel> weaponModels = WeaponService.getInstance().getAll();

        this.response.init(Outgoing.weaponVendingMachineDataComposer);

        for (WeaponModel weaponModel : weaponModels) {
            this.response.appendString(
                    weaponModel.getId() + ";" +
                         weaponModel.getUniqueName() + ";" +
                         weaponModel.getDisplayName() + ";" +
                         weaponModel.getType() + ";"
            );
        }

        return this.response;
    }
}
