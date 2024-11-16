package com.us.archangel;

import com.us.archangel.bounty.BountyManager;
import com.us.archangel.government.GovernmentManager;
import com.us.archangel.police.PoliceManager;
import com.us.nova.core.DatabaseConfig;
import com.us.archangel.corp.CorpManager;
import com.us.archangel.crime.CrimeManager;
import com.us.archangel.feature.RoleplayFeatureManager;
import com.us.archangel.gang.GangManager;
import com.us.archangel.sanction.SanctionManager;
import com.us.archangel.player.PlayerManager;
import com.us.archangel.weapon.WeaponManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Getter
public class Archangel {

    private static final Logger LOGGER = LoggerFactory.getLogger(Archangel.class);

    private BountyManager bountyManager;
    private GangManager gangManager;
    private GovernmentManager governmentManager;
    private CorpManager corpManager;
    private CrimeManager crimeManager;
    private SanctionManager sanctionManager;
    private PlayerManager playerManager;
    private PoliceManager policeManager;
    private RoleplayFeatureManager roleplayFeatureManager;
    private WeaponManager weaponManager;

    public void load() {
        long millis = System.currentTimeMillis();

        this.bountyManager = BountyManager.getInstance();
        this.corpManager = CorpManager.getInstance();
        this.crimeManager = CrimeManager.getInstance();
        this.gangManager = GangManager.getInstance();
        this.governmentManager = GovernmentManager.getInstance();
        this.sanctionManager = SanctionManager.getInstance();
        this.playerManager = PlayerManager.getInstance();
        this.policeManager = PoliceManager.getInstance();
        this.roleplayFeatureManager = RoleplayFeatureManager.getInstance();
        this.weaponManager = WeaponManager.getInstance();

        LOGGER.info("Archangel -> Loaded! (" + (System.currentTimeMillis() - millis) + " MS)");
    }

    public void dispose() {
        this.bountyManager.dispose();
        this.corpManager.dispose();;
        this.crimeManager.dispose();
        this.gangManager.dispose();
        this.governmentManager.dispose();
        this.sanctionManager.dispose();
        this.playerManager.dispose();
        this.policeManager.dispose();
        this.roleplayFeatureManager.disposeFeatures();
        this.weaponManager.dispose();;

        DatabaseConfig.shutdown();

        log.info("Archangel -> Disposed!");
    }
}
