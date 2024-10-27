package com.us.roleplay.billing.items;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.roleplay.corp.LicenseType;

public record WeaponLicenseBillingItem(int userID, int chargedByUserID) implements BillingItem {

    @Override
    public BillType getType() {
        return BillType.WEAPON_LICENSE;
    }

    @Override
    public String getTitle() {
        return "WeaponModel License";
    }

    @Override
    public String getDescription() {
        return "Fee for background check and processing";
    }

    @Override
    public int getAmountOwed() {
        return 500;
    }

    @Override
    public int getAmountPaid() {
        return 0;
    }

    @Override
    public int getChargedByCorpID() {
        return Emulator.getGameEnvironment().getHabboManager().getHabbo(this.chargedByUserID).getPlayer().getCorp().getId();
    }

    @Override
    public void onBillPaid(Habbo habbo) {
        habbo.getInventory().getLicensesComponent().createLicense(LicenseType.WEAPON);
        habbo.shout(Emulator.getTexts().getValue("roleplay.license.received").replace(":license", this.getTitle()));
    }
}
