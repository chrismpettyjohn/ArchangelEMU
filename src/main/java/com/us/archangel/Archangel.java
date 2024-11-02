package com.us.archangel;

import com.us.archangel.bounty.BountyManager;
import com.us.nova.core.DatabaseConfig;
import com.us.archangel.corp.CorpManager;
import com.us.archangel.crime.CrimeManager;
import com.us.archangel.feature.RoleplayFeatureManager;
import com.us.archangel.gang.GangManager;
import com.us.archangel.sanction.SanctionManager;
import com.us.archangel.player.PlayerManager;
import com.us.archangel.weapon.WeaponManager;
import com.eu.habbo.core.ConfigurationManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Getter
public class Archangel {

    private static final Logger LOGGER = LoggerFactory.getLogger(Archangel.class);

    private BountyManager bountyManager;
    private GangManager gangManager;
    private CorpManager corpManager;
    private CrimeManager crimeManager;
    private SanctionManager sanctionManager;
    private PlayerManager playerManager;
    private RoleplayFeatureManager roleplayFeatureManager;
    private WeaponManager weaponManager;

    public void load(ConfigurationManager config) {
        long millis = System.currentTimeMillis();

        DatabaseConfig.initialize(config);

        SessionFactory sessionFactory = DatabaseConfig.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.bountyManager = BountyManager.getInstance();
        this.corpManager = CorpManager.getInstance();
        this.crimeManager = CrimeManager.getInstance();
        this.gangManager = GangManager.getInstance();
        this.sanctionManager = SanctionManager.getInstance();
        this.playerManager = PlayerManager.getInstance();
        this.roleplayFeatureManager = RoleplayFeatureManager.getInstance();
        this.weaponManager = WeaponManager.getInstance();

        LOGGER.info("Archangel -> Loaded! (" + (System.currentTimeMillis() - millis) + " MS)");
    }

    public void dispose() {
        this.bountyManager.dispose();
        this.corpManager.dispose();;
        this.crimeManager.dispose();
        this.sanctionManager.dispose();
        this.playerManager.dispose();
        this.roleplayFeatureManager.disposeFeatures();
        this.weaponManager.dispose();;

        DatabaseConfig.shutdown();

        log.info("Archangel -> Disposed!");
    }
}
