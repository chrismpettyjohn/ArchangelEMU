package com.us.archangel.feature.combat.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.us.archangel.ammo.enums.AmmoType;
import com.us.archangel.feature.combat.packets.outgoing.CombatDelayComposer;
import com.us.archangel.feature.combat.packets.outgoing.UserDiedComposer;
import com.us.archangel.feature.hospital.actions.TeleportHospitalAction;
import com.us.archangel.feature.player.packets.outgoing.UserRoleplayStatsChangeComposer;
import com.us.archangel.player.enums.PlayerAction;
import com.us.archangel.player.model.PlayerKillHistoryModel;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.service.PlayerKillHistoryService;
import com.us.archangel.room.enums.RoomType;
import com.us.archangel.weapon.enums.WeaponEffect;
import com.us.archangel.weapon.enums.WeaponType;
import com.us.nova.core.NotificationHelper;

import java.util.concurrent.TimeUnit;

public class UserAttackEvent extends MessageHandler {

    @Override
    public void handle() {

        if (this.client.getHabbo().getPlayer().isPassiveMode()) {
            this.client.getHabbo().whisper("You can't attack in passive mode!");
            return;
        }

        if (!this.client.getHabbo().getPlayer().canInteract() || this.client.getHabbo().getPlayer().isCombatBlocked()) {
            this.client.getHabbo().whisper("You need to wait a bit before attacking again.");
            this.client.sendResponse(new CombatDelayComposer(this.client.getHabbo()));
            return;
        }

        PlayerWeaponModel playerWeapon = this.client.getHabbo().getInventory().getWeaponsComponent().getEquippedWeapon();

        if (playerWeapon != null && playerWeapon.getWeapon() != null && playerWeapon.getWeapon().getType() == WeaponType.GUN) {
            this.client.getHabbo().whisper("You are out of ammo");
            if (playerWeapon.getAmmoRemaining() <= 0) {
                return;
            }
        }

        int x = this.packet.readInt();
        int y = this.packet.readInt();
        PlayerWeaponModel equippedWeapon = this.client.getHabbo().getInventory().getWeaponsComponent().getEquippedWeapon();

        RoomTile roomTile = this.client.getHabbo().getRoomUnit().getRoom().getLayout().getTile((short) x, (short) y);
        if (roomTile == null || this.client.getHabbo().getRoomUnit().getRoom().getRoomInfo().getTags().contains(RoomType.PASSIVE)) {
            return;
        }

        Habbo targetedHabbo = getTargetedHabbo(x, y);
        if (targetedHabbo == null || !isInRange(x, y, equippedWeapon)) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.attack.target_missed"));
            return;
        }

        if (targetedHabbo.getPlayer().isPassiveMode()) {
            this.client.getHabbo().whisper("You can't attack a passive player!");
            return;
        }

        if (!hasEnoughEnergy(targetedHabbo)) {
            return;
        }

        int totalDamage = calculateDamage(equippedWeapon);
        performAttack(targetedHabbo, totalDamage, equippedWeapon);


        if (targetedHabbo.getPlayer().isDead()) {
            // Record kill
            PlayerKillHistoryModel playerDeathRecord = new PlayerKillHistoryModel();
            playerDeathRecord.setAttackerUserId(this.client.getHabbo().getHabboInfo().getId());
            playerDeathRecord.setVictimUserId(targetedHabbo.getHabboInfo().getId());
            playerDeathRecord.setAttackerWeaponId(playerWeapon != null ? playerWeapon.getWeaponId() : 0);
            PlayerKillHistoryService.getInstance().create(playerDeathRecord);

            // Teleport victim to hospital
            Emulator.getThreading().run(new TeleportHospitalAction(targetedHabbo));

            // Notify online users
            NotificationHelper.sendOnline(new UserDiedComposer(targetedHabbo, this.client.getHabbo()));
            NotificationHelper.announceOnline(this.client.getHabbo().getHabboInfo().getId() == targetedHabbo.getHabboInfo().getId()
                    ? this.client.getHabbo().getHabboInfo().getUsername() + " committed suicide"
                    : this.client.getHabbo().getHabboInfo().getUsername() + " killed " + targetedHabbo.getHabboInfo().getUsername()
            );
        }
    }

    private Habbo getTargetedHabbo(int x, int y) {
        return this.client.getHabbo().getRoomUnit().getRoom().getRoomUnitManager()
                .getHabbosAt(this.client.getHabbo().getRoomUnit().getRoom().getLayout().getTile((short) x, (short) y))
                .stream().findFirst().orElse(null);
    }

    private boolean isInRange(int x, int y, PlayerWeaponModel equippedWeapon) {
        int rangeInTiles = equippedWeapon != null ? equippedWeapon.getWeapon().getRangeInTiles() : 1;
        int distanceX = Math.abs(x - this.client.getHabbo().getRoomUnit().getCurrentPosition().getX());
        int distanceY = Math.abs(y - this.client.getHabbo().getRoomUnit().getCurrentPosition().getY());
        return distanceX <= rangeInTiles && distanceY <= rangeInTiles;
    }

    private boolean hasEnoughEnergy(Habbo targetedHabbo) {
        int totalEnergy = Emulator.getConfig().getInt("roleplay.attack.energy", 8);
        if (totalEnergy > this.client.getHabbo().getPlayer().getEnergyNow()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("commands.roleplay.out_of_energy")
                    .replace(":username", targetedHabbo.getHabboInfo().getUsername()));
            return false;
        }
        return true;
    }

    private int calculateDamage(PlayerWeaponModel equippedWeapon) {
        if (equippedWeapon != null && equippedWeapon.getPlayerAmmo().getAmmo().getType() == AmmoType.STUN) {
            return 0;
        }

        return this.client.getHabbo().getPlayerSkills().getDamageModifier(equippedWeapon != null ? equippedWeapon.getWeapon() : null);
    }

    private void performAttack(Habbo targetedHabbo, int baseDamage, PlayerWeaponModel equippedWeapon) {
        int totalDamage = baseDamage;

        if (equippedWeapon == null) {
            this.client.getHabbo().getPlayerSkills().addMeleeXp(totalDamage);
        }

        if (equippedWeapon != null) {
            this.client.getHabbo().getPlayerSkills().addWeaponXp(totalDamage);


            if (equippedWeapon.getWeapon().getType() == WeaponType.GUN) {
                equippedWeapon.depleteAmmo(1);
            }

            if (equippedWeapon.getWeapon().getEffect() == WeaponEffect.BLEED) {
                int bleedDmgPerSec = Math.max(totalDamage / 5, 1);
                for (int i = 1; i <= 5; i++) {
                    Emulator.getThreading().run(() -> {
                        targetedHabbo.getPlayer().depleteHealth(bleedDmgPerSec);
                        targetedHabbo.whisper(Emulator.getTexts().getValue("roleplay.attack.bleed").replace(":damage", String.valueOf(bleedDmgPerSec)));
                        }, TimeUnit.SECONDS.toMillis(i)
                    );
                }
            }

            switch (equippedWeapon.getPlayerAmmo().getAmmo().getType()) {
                case STANDARD:
                    totalDamage += 1;
                    break;

                case FMJ:
                    totalDamage += 2;
                    break;

                case ARMOR_PIERCING:
                    totalDamage += 5;
                    break;

                case STUN:
                    targetedHabbo.getPlayer().setCurrentAction(PlayerAction.Stunned);
                    Emulator.getThreading().run(() ->
                                    targetedHabbo.getPlayer().setCurrentAction(PlayerAction.None),
                            TimeUnit.SECONDS.toMillis(10));
                    break;
            }
        }

        if (equippedWeapon == null || equippedWeapon.getWeapon().getType() != WeaponType.TOOL) {
            targetedHabbo.getPlayer().depleteHealth(totalDamage);
        }

        if (equippedWeapon != null && equippedWeapon.getWeapon().getEffect() == WeaponEffect.HEAL) {
            targetedHabbo.getPlayer().addHealth(totalDamage);
        }

        if (equippedWeapon != null && equippedWeapon.getWeapon().getEffect() == WeaponEffect.LOCKPICK) {
            if (targetedHabbo.getPlayer().getCurrentAction() != PlayerAction.Cuffed) {
                this.client.getHabbo().whisper(":username is not cuffed!");
                return;
            }

            targetedHabbo.getPlayer().setCurrentAction(PlayerAction.None);
        }

        this.client.getHabbo().getPlayer().depleteEnergy(Emulator.getConfig().getInt("roleplay.attack.energy", 8));

        String attackMessage = equippedWeapon != null
                ? equippedWeapon.getWeapon().getAttackMessage()
                : Emulator.getTexts().getValue("commands.roleplay.cmd_hit_success");

        this.client.getHabbo().shout(attackMessage
                .replace(":username", targetedHabbo.getHabboInfo().getUsername())
                .replace(":damage", Integer.toString(totalDamage))
                .replace(":displayName", equippedWeapon != null ? equippedWeapon.getWeapon().getDisplayName() : ""));


        NotificationHelper.sendRoom(this.client.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId(), new UserRoleplayStatsChangeComposer(targetedHabbo));
        NotificationHelper.sendRoom(this.client.getHabbo().getRoomUnit().getRoom().getRoomInfo().getId(), new UserRoleplayStatsChangeComposer(this.client.getHabbo()));
    }
}
