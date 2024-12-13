package com.us.archangel.feature.weapon.packets.outgoing;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.us.archangel.weapon.model.WeaponModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WeaponDataComposer extends MessageComposer {
    protected final WeaponModel weapon;

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.weaponDataComposer);

        this.response.appendInt(this.weapon.getId());
        this.response.appendString(this.weapon.getUniqueName());
        this.response.appendString(this.weapon.getDisplayName());
        this.response.appendString(this.weapon.getType().toString());
        this.response.appendInt(this.weapon.getMinDamage());
        this.response.appendInt(this.weapon.getMaxDamage());
        this.response.appendInt(this.weapon.getRangeInTiles());
        this.response.appendInt(this.weapon.getAccuracy());
        this.response.appendInt(this.weapon.getReloadTime());
        this.response.appendString(this.weapon.getReloadMessage());
        this.response.appendInt(this.weapon.getAmmoCapacity());
        this.response.appendInt(this.weapon.getWeight());
        this.response.appendInt(this.weapon.getCooldownSeconds());
        this.response.appendInt(this.weapon.getEquipEffect());
        this.response.appendString(this.weapon.getEquipMessage());
        this.response.appendString(this.weapon.getUnequipMessage());
        this.response.appendString(this.weapon.getAttackMessage());

        return this.response;
    }
}