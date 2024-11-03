package com.us.archangel.feature.weapon.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.weapon.model.WeaponModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class WeaponListComposer extends MessageComposer {
    protected final List<WeaponModel> weaponModels;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.weaponListComposer);

        this.response.appendInt(this.weaponModels.size());

        for (WeaponModel playerWeapon : this.weaponModels) {
            this.response.appendString(
                    String.format("%s;%s;%s;%s;%s;%s;%s%s",
                            playerWeapon.getId(),
                            playerWeapon.getUniqueName(),
                            playerWeapon.getDisplayName(),
                            playerWeapon.getType(),
                            playerWeapon.getMinDamage(),
                            playerWeapon.getMaxDamage(),
                            playerWeapon.getAccuracy(),
                            playerWeapon.getAmmoCapacity()
                    )
            );
        }
        return this.response;
    }
}
