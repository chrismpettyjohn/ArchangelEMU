package com.us.archangel.feature.store.packets.incoming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.rooms.users.AvatarEffectMessageComposer;
import com.eu.habbo.messages.outgoing.rooms.users.CarryObjectMessageComposer;
import com.eu.habbo.messages.outgoing.users.CreditBalanceComposer;
import com.us.archangel.ammo.enums.AmmoSize;
import com.us.archangel.ammo.enums.AmmoType;
import com.us.archangel.ammo.model.AmmoModel;
import com.us.archangel.ammo.service.AmmoService;
import com.us.archangel.player.entity.PlayerWeaponEntity;
import com.us.archangel.player.mapper.PlayerWeaponMapper;
import com.us.archangel.player.service.PlayerAmmoService;
import com.us.archangel.player.service.PlayerWeaponService;
import com.us.archangel.store.enums.StoreProductOfferStatus;
import com.us.archangel.store.models.StoreProductOfferModel;
import com.us.archangel.store.service.StoreProductOfferService;
import com.us.archangel.weapon.enums.WeaponType;
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

                    if (weaponModel.getAmmoSize() != AmmoSize.NONE) {
                        AmmoModel weaponAmmoModel = AmmoService.getInstance().getBySizeAndType(weaponModel.getAmmoSize(), AmmoType.STANDARD);

                        if (weaponAmmoModel == null) {
                            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.store_offer.transaction_failed"));
                            throw new RuntimeException("weapon has no matching ammo");
                        }
                        PlayerWeaponService.getInstance().createWithAmmo(
                                weaponModel,
                                this.client.getHabbo().getHabboInfo().getId(),
                                weaponAmmoModel
                        );
                    }
                    if (weaponModel.getAmmoSize() == AmmoSize.NONE) {
                        PlayerWeaponEntity playerWeaponEntity = new PlayerWeaponEntity();
                        playerWeaponEntity.setUserId(this.client.getHabbo().getHabboInfo().getId());
                        playerWeaponEntity.setWeaponId(weaponModel.getId());
                        PlayerWeaponService.getInstance().create(PlayerWeaponMapper.toModel(playerWeaponEntity));
                    }

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


            Habbo employeeHabbo = this.client.getHabbo().getRoomUnit().getRoom().getRoomUnitManager().getRoomHabboById(offerModel.getEmployeeUserId());

            if (employeeHabbo != null) {
                employeeHabbo.getRoomUnit().setHandItem(0);
                employeeHabbo.getRoomUnit().setEffectId(0);
                employeeHabbo.getRoomUnit().instantUpdate();
                employeeHabbo.getRoomUnit().getRoom().sendComposer(new CarryObjectMessageComposer(employeeHabbo.getRoomUnit()).compose());
                employeeHabbo.getRoomUnit().getRoom().sendComposer(new AvatarEffectMessageComposer(employeeHabbo.getRoomUnit()).compose());
            }
        }

        offerModel.setOfferStatus(canAfford ? StoreProductOfferStatus.ACCEPTED : StoreProductOfferStatus.REJECTED);
        StoreProductOfferService.getInstance().update(offerModel.getId(), offerModel);
    }
}