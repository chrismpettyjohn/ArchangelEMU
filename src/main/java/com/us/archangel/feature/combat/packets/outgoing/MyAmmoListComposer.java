package com.us.archangel.feature.combat.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.player.model.PlayerAmmoModel;
import com.us.archangel.player.service.PlayerAmmoService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MyAmmoListComposer extends MessageComposer {
    private final Habbo habbo;

    @Override
    protected ServerMessage composeInternal() {
        List<PlayerAmmoModel> habboAmmo = PlayerAmmoService.getInstance().getByUserID(this.habbo.getHabboInfo().getId());
        this.response.init(Outgoing.myAmmoListComposer);
        for (PlayerAmmoModel ammo : habboAmmo) {
            this.response.appendString(
                    ammo.getId()
                            + ";" +  ammo.getAmmo().getId()
                            + ";" +  ammo.getAmmo().getUniqueName()
                            + ";" + ammo.getAmmo().getDisplayName()
                            + ";" + ammo.getAmmo().getSize()
                            + ";" + ammo.getAmmo().getType()
            );
        }
        return this.response;
    }
}
