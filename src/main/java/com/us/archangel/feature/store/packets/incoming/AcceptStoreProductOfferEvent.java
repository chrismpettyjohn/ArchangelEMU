package com.us.archangel.feature.store.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.users.CreditBalanceComposer;
import com.eu.habbo.threading.runnables.RoomUnitGiveHanditem;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.service.AmmoService;
import com.us.archangel.feature.gunstore.packets.incoming.AmmoCrateTakeEvent;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.service.PlayerAmmoService;
import com.us.archangel.player.service.PlayerWeaponService;
import com.us.archangel.store.enums.StoreProductOfferStatus;
import com.us.archangel.store.models.StoreProductOfferModel;
import com.us.archangel.store.service.StoreProductOfferService;
import com.us.archangel.weapon.model.WeaponModel;
import com.us.archangel.weapon.service.WeaponService;

public class AcceptStoreProductOfferEvent extends MessageHandler {
    @Override
    public void handle() {
        int offerId = this.packet.readInt();

        StoreProductOfferModel offerModel = StoreProductOfferService.getInstance().getById(offerId);

        if (offerModel == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.store_offer.not_found"));
            return;
        }

        if (offerModel.getRecipientUserId() != this.client.getHabbo().getHabboInfo().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.store_offer.not_found"));
            return;
        }

        if (offerModel.getOfferStatus() != StoreProductOfferStatus.PENDING) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.store_offer.not_found"));
            return;
        }

        boolean canAfford = this.client.getHabbo().getHabboInfo().getCredits() >= offerModel.getProductCost();

        if (!canAfford) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.store_offer.not_enough_credits"));
        }

        if (canAfford) {
            Habbo employee = this.client.getHabbo().getRoomUnit().getRoom().getRoomUnitManager().getRoomHabboById(offerModel.getEmployeeUserId());

            if (employee != null) {
                employee.getInventory().getStoreShiftComponent().removeProductById(offerModel.getProductId());
                // TODO: Remove from corp inventory
            }

            switch (offerModel.getProductType()) {
                case AMMO:
                    AmmoModel ammoModel = AmmoService.getInstance().getById(offerModel.getProductId());

                    if (ammoModel == null) {
                        throw new RuntimeException("ammo not found");
                    }

                    // TODO: change quantity
                    PlayerAmmoService.getInstance().addAmmo(this.client.getHabbo().getHabboInfo().getId(), ammoModel.getId(), 30);

                    break;
                case WEAPON:
                    WeaponModel weaponModel = WeaponService.getInstance().getById(offerModel.getProductId());

                    if (weaponModel == null) {
                        this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.store_offer.transaction_failed"));
                        throw new RuntimeException("weapon not found");
                    }

                    AmmoModel weaponAmmoModel = AmmoService.getInstance().getAll().stream().filter(ammo -> ammo.getSize() == weaponModel.getAmmoSize()).findFirst().orElse(null);

                    if (weaponAmmoModel == null) {
                        this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.store_offer.transaction_failed"));
                        throw new RuntimeException("weapon has no matching ammo");
                    }

                    PlayerAmmoService.getInstance().addAmmo(this.client.getHabbo().getHabboInfo().getId(), weaponAmmoModel.getId(), weaponModel.getAmmoCapacity() * 2);

                    // Give player weapon
                    PlayerWeaponModel playerWeaponModel = new PlayerWeaponModel();
                    playerWeaponModel.setWeaponId(offerModel.getProductId());
                    playerWeaponModel.setUserId(this.client.getHabbo().getHabboInfo().getId());
                    playerWeaponModel.setAmmoId(weaponAmmoModel.getId());
                    playerWeaponModel.setAmmoRemaining(weaponModel.getAmmoCapacity());
                    PlayerWeaponService.getInstance().create(playerWeaponModel);
                    break;
                case ITEM:
                    throw new RuntimeException("not supported yet");
            }

            this.client.getHabbo().getHabboInfo().setCredits(this.client.getHabbo().getHabboInfo().getCredits() - offerModel.getProductCost());
            this.client.getHabbo().getClient().sendResponse(new CreditBalanceComposer(this.client.getHabbo()));
            this.client.getHabbo().shout(
                    Emulator.getTexts().getValue("roleplay.store_offer.accepted")
                            .replace(":fee", String.valueOf(offerModel.getProductCost()))
                            .replace(":item", offerModel.getProductType().toString())
            );

            Habbo employeeHabbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(offerModel.getEmployeeUserId());

            if (employeeHabbo != null) {
                Emulator.getThreading().run(new RoomUnitGiveHanditem(employeeHabbo.getRoomUnit(), employeeHabbo.getRoomUnit().getRoom(), 0));
                employeeHabbo.getRoomUnit().setEffectId(0);
                employeeHabbo.getRoomUnit().instantUpdate();
            }
        }

        offerModel.setOfferStatus(canAfford ? StoreProductOfferStatus.ACCEPTED : StoreProductOfferStatus.REJECTED);
        StoreProductOfferService.getInstance().update(offerModel.getId(), offerModel);
    }
}
