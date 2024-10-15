package com.us.archangel;

import com.us.archangel.core.DatabaseConfig;
import com.us.archangel.corp.CorpManager;
import com.us.archangel.feature.RoleplayFeatureManager;
import com.us.archangel.gang.GangManager;
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

    private WeaponManager weaponManager;

    private CorpManager corpManager;

    private GangManager gangManager;

    private PlayerManager playerManager;

    private RoleplayFeatureManager roleplayFeatureManager;

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

        this.weaponManager = WeaponManager.getInstance(sessionFactory);
        this.corpManager = CorpManager.getInstance(sessionFactory);
        this.gangManager = GangManager.getInstance(sessionFactory);
        this.playerManager = PlayerManager.getInstance(sessionFactory);
        this.roleplayFeatureManager = RoleplayFeatureManager.getInstance();

        DatabaseConfig.shutdown();


        LOGGER.info("Archangel -> Loaded! (" + (System.currentTimeMillis() - millis) + " MS)");
    }

    public void dispose() {
        log.info("Archangel -> Disposed!");
    }
}
