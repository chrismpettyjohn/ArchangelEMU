package com.us.roleplay.users.inventory;

import com.eu.habbo.habbohotel.users.Habbo;
import com.us.archangel.player.entity.PlayerWeaponEntity;
import com.us.archangel.player.mapper.PlayerWeaponMapper;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.service.PlayerWeaponService;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class WeaponsComponent {

    @Setter
    @Getter
    private PlayerWeaponModel equippedWeapon;

    public final Habbo habbo;
    public WeaponsComponent(Habbo habbo) {
        this.habbo = habbo;

        if(habbo.getHabboInfo().getPermissionGroup().getRoomEffect() > 0)
            this.createWeapon(habbo.getHabboInfo().getPermissionGroup().getRoomEffect());
    }

    public PlayerWeaponModel getWeaponByUniqueName(String uniqueName) {
        List<PlayerWeaponModel> playerWeapons = PlayerWeaponService.getInstance().getByUserID(this.habbo.getHabboInfo().getId());
        for (PlayerWeaponModel playerWeapon : playerWeapons) {
            if (playerWeapon.getWeapon().getUniqueName().equals(uniqueName)) {
                return playerWeapon;
            }
        }
        return null;
    }

    public void createWeapon(int weaponID) {
        WeaponModel weapon = WeaponService.getInstance().getById(weaponID);
        PlayerWeaponEntity playerWeapon = new PlayerWeaponEntity();
        playerWeapon.setUserId(this.habbo.getPlayer().getId());
        playerWeapon.setWeaponId(weaponID);
        playerWeapon.setAmmoRemaining(weapon.getAmmoCapacity());
        PlayerWeaponService.getInstance().create(PlayerWeaponMapper.toModel(playerWeapon));
    }

    public void dispose() {
        List<PlayerWeaponModel> playerWeapons = PlayerWeaponService.getInstance().getByUserID(this.habbo.getHabboInfo().getId());
        for (PlayerWeaponModel playerWeapon : playerWeapons) {
            PlayerWeaponService.getInstance().update(playerWeapon.getId(), playerWeapon);
        }
    }

}