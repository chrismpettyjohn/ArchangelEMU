package com.us.archangel.player.model;

import com.us.archangel.player.service.PlayerAmmoService;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PlayerWeaponModel {
    private int id;
    private int userId;
    private int weaponId;
    private int ammoId;
    private int ammoRemaining;

    public PlayerWeaponModel() {
    }

    public WeaponModel getWeapon() {
        return WeaponService.getInstance().getById(this.weaponId);
    }

    public PlayerAmmoModel getPlayerAmmo() {
        return PlayerAmmoService.getInstance().getByUserAndAmmoId(this.userId, this.ammoId);
    }

    public void depleteAmmo(int ammoUsed) {
       this.ammoRemaining = Math.min(this.ammoRemaining - ammoUsed, 0);
    }
}

