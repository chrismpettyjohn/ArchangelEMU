package com.us.roleplay.billing.items;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.us.roleplay.corp.LicenseType;

public record MiningLicenseBillingItem(int userID, int chargedByUserID) implements BillingItem {

    @Override
    public BillType getType() {
        return BillType.MINING_LICENSE;
    }

    @Override
    public String getTitle() {
        return "Mining License";
    }

    @Override
    public String getDescription() {
        return "Fee for processing";
    }

    @Override
    public int getAmountOwed() {
        return 150;
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
        habbo.getInventory().getLicensesComponent().createLicense(LicenseType.MINING);
        habbo.shout(Emulator.getTexts().getValue("roleplay.license.received").replace(":license", this.getTitle()));
    }
}
