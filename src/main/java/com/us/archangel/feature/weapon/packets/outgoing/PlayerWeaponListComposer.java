package com.us.archangel.feature.weapon.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.player.model.PlayerWeaponModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PlayerWeaponListComposer extends MessageComposer {
    protected final List<PlayerWeaponModel> playerWeaponModels;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.playerWeaponListComposer);

        this.response.appendInt(this.playerWeaponModels.size());

        for (PlayerWeaponModel playerWeapon : this.playerWeaponModels) {
            this.response.appendString(
                    String.format("%s;%s;%s;%s;%s;%s",
                            playerWeapon.getId(),
                            playerWeapon.getWeaponId(),
                            playerWeapon.getWeapon().getDisplayName(),
                            playerWeapon.getWeapon().getType(),
                            playerWeapon.getAmmoRemaining(),
                            playerWeapon.getWeapon().getAmmoCapacity()
                    )
            );
        }

        return this.response;
    }
}
