package com.eu.habbo.habbohotel.users;

import com.eu.habbo.habbohotel.catalog.marketplace.MarketPlace;
import com.eu.habbo.habbohotel.catalog.marketplace.MarketPlaceOffer;
import com.eu.habbo.habbohotel.catalog.marketplace.MarketPlaceState;
import com.eu.habbo.habbohotel.users.inventory.*;
import com.us.roleplay.users.inventory.HotBarComponent;
import com.us.roleplay.users.inventory.LicensesComponent;
import com.us.archangel.store.inventory.StoreShiftComponent;
import com.us.roleplay.users.inventory.WeaponsComponent;
import gnu.trove.set.hash.THashSet;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class HabboInventory {
    //Configuration. Loaded from database & updated accordingly.
    public static int MAXIMUM_ITEMS = 10000;
    private final THashSet<MarketPlaceOffer> items;
    private final Habbo habbo;
    private WardrobeComponent wardrobeComponent;
    private BadgesComponent badgesComponent;
    private BotsComponent botsComponent;
    private EffectsComponent effectsComponent;
    private ItemsComponent itemsComponent;
    private PetsComponent petsComponent;

    @Setter
    @Getter
    private WeaponsComponent weaponsComponent;

    @Setter
    @Getter
    private LicensesComponent licensesComponent;

    @Getter
    @Setter
    private HotBarComponent hotBarComponent;

    @Setter
    @Getter
    private StoreShiftComponent storeShiftComponent;

    public HabboInventory(Habbo habbo) {
        this.habbo = habbo;
        try {
            this.badgesComponent = new BadgesComponent(this.habbo);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }

        try {
            this.botsComponent = new BotsComponent(this.habbo);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }

        try {
            this.effectsComponent = new EffectsComponent(this.habbo);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }

        try {
            this.itemsComponent = new ItemsComponent(this, this.habbo);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }

        try {
            this.petsComponent = new PetsComponent(this.habbo);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }

        try {
            this.wardrobeComponent = new WardrobeComponent(this.habbo);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }

        try {
            this.weaponsComponent = new WeaponsComponent(this.habbo);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }

        try {
            this.licensesComponent = new LicensesComponent(this.habbo);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }

        try {
            this.hotBarComponent = new HotBarComponent(this.habbo);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }

        try {
            this.storeShiftComponent = new StoreShiftComponent(this.habbo);
        } catch (Exception e) {
            log.error("Caught exception", e);
        }


        this.items = MarketPlace.getOwnOffers(this.habbo);
    }



    public void dispose() {
        this.badgesComponent.dispose();
        this.botsComponent.dispose();
        this.effectsComponent.dispose();
        this.itemsComponent.dispose();
        this.petsComponent.dispose();
        this.wardrobeComponent.dispose();
        this.weaponsComponent.dispose();
        this.hotBarComponent.dispose();

        this.badgesComponent = null;
        this.botsComponent = null;
        this.effectsComponent = null;
        this.itemsComponent = null;
        this.petsComponent = null;
        this.wardrobeComponent = null;
        this.weaponsComponent = null;
        this.hotBarComponent = null;
    }

    public void addMarketplaceOffer(MarketPlaceOffer marketPlaceOffer) {
        this.items.add(marketPlaceOffer);
    }

    public void removeMarketplaceOffer(MarketPlaceOffer marketPlaceOffer) {
        this.items.remove(marketPlaceOffer);
    }

    public THashSet<MarketPlaceOffer> getMarketplaceItems() {
        return this.items;
    }

    public int getSoldPriceTotal() {
        int i = 0;
        for (MarketPlaceOffer offer : this.items) {
            if (offer.getState().equals(MarketPlaceState.SOLD)) {
                i += offer.getPrice();
            }
        }
        return i;
    }

    public MarketPlaceOffer getOffer(int id) {
        synchronized (this.items) {
            for (MarketPlaceOffer offer : this.items) {
                if (offer.getOfferId() == id)
                    return offer;
            }
        }

        return null;
    }

}
