package com.us.archangel.player.model;

import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PlayerWeaponModel {
    private int id;
    private int userId;
    private int weaponId;
    @Setter
    private int ammoRemaining;

    public void depleteAmmo(int ammo) {
        this.ammoRemaining = Math.min(this.ammoRemaining - ammo, 0);
    }

    public WeaponModel getWeapon() {
        return WeaponService.getInstance().getById(this.weaponId);
    }

}

