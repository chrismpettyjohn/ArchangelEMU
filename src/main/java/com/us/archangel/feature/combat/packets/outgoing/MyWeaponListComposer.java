package com.us.archangel.feature.combat.packets.outgoing;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.service.PlayerWeaponService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MyWeaponListComposer extends MessageComposer {
    private final Habbo habbo;

    @Override
    protected ServerMessage composeInternal() {
        List<PlayerWeaponModel> habboWeapons = PlayerWeaponService.getInstance().getByUserID(this.habbo.getHabboInfo().getId());
        this.response.init(Outgoing.myWeaponListComposer);
        for (PlayerWeaponModel weapon : habboWeapons) {
            this.response.appendString(
                    weapon.getId()
                            + ";" +  weapon.getWeapon().getId()
                            + ";" +  weapon.getWeapon().getUniqueName()
                            + ";" + weapon.getWeapon().getDisplayName()
                            + ";" + weapon.getWeapon().getEquipEffect()
                            + ";" + weapon.getWeapon().getAmmoCapacity()
            );
        }
        return this.response;
    }
}
