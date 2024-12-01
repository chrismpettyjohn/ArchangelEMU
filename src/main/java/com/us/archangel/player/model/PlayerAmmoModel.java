package com.us.archangel.player.model;

import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.service.AmmoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PlayerAmmoModel {
    private int id;
    private int userId;
    private int ammoId;
    @Setter
    private int ammoRemaining;

    public PlayerAmmoModel() {
    }

    public void depleteAmmo(int ammo) {
            this.ammoRemaining = Math.max(this.ammoRemaining - ammo, 0);
        }

    public AmmoModel getAmmo() {
        return AmmoService.getInstance().getById(this.ammoId);
    }

}

