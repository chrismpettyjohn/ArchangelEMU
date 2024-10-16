package com.us.roleplay.corp;

import com.us.archangel.corp.enums.CorpIndustry;

public class LicenseMapper {

    public static CorpIndustry licenseTypeToCorpTag(LicenseType licenseType) {
        if (licenseType == LicenseType.DRIVER) {
            return CorpIndustry.DriversEd;
        }

        if (licenseType == LicenseType.FARMING) {
            return CorpIndustry.Farm;
        }

        if (licenseType == LicenseType.FISHING) {
            return CorpIndustry.Fish;
        }

        if (licenseType == LicenseType.MINING) {
            return CorpIndustry.Mine;
        }

        if (licenseType == LicenseType.LUMBERJACK) {
            return CorpIndustry.Lumber;
        }

        if (licenseType == LicenseType.WEAPON) {
            return CorpIndustry.GunStore;
        }

        return null;
    }

    public static LicenseType corpIndustryToLicenseType(CorpIndustry corpTag) {
        if (corpTag == CorpIndustry.DriversEd) {
            return LicenseType.DRIVER;
        }

        if (corpTag == CorpIndustry.Farm) {
            return LicenseType.FARMING;
        }

        if (corpTag == CorpIndustry.Fish) {
            return LicenseType.FISHING;
        }

        if (corpTag == CorpIndustry.Mine) {
            return LicenseType.MINING;
        }

        if (corpTag == CorpIndustry.Lumber) {
            return LicenseType.LUMBERJACK;
        }

        if (corpTag == CorpIndustry.GunStore) {
            return LicenseType.WEAPON;
        }

        return null;
    }

}
