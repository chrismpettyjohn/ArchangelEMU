package com.us.nova;

import com.eu.habbo.core.ConfigurationManager;
import com.us.nova.betacode.BetaCodeManager;
import com.us.nova.core.DatabaseConfig;
import com.us.nova.user.UserManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Getter
public class Nova {

    private static final Logger LOGGER = LoggerFactory.getLogger(Nova.class);

    private BetaCodeManager betaCodeManager;
    private UserManager userManager;

    public void load(ConfigurationManager config) {
        long millis = System.currentTimeMillis();

        DatabaseConfig.initialize(config);

        SessionFactory sessionFactory = DatabaseConfig.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            this.betaCodeManager = BetaCodeManager.getInstance(sessionFactory);
            this.userManager = UserManager.getInstance(sessionFactory);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        LOGGER.info("Nova -> Loaded! (" + (System.currentTimeMillis() - millis) + " MS)");
    }

    public void dispose() {
        this.userManager.dispose();

        DatabaseConfig.shutdown();

        log.info("Nova -> Disposed!");
    }
}
