package com.eu.archangel;

import com.eu.archangel.core.DatabaseConfig;
import com.eu.habbo.core.ConfigurationManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Archangel {

    private static final Logger LOGGER = LoggerFactory.getLogger(Archangel.class);

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

        DatabaseConfig.shutdown();


        LOGGER.info("Archangel -> Loaded! (" + (System.currentTimeMillis() - millis) + " MS)");
    }

    public void dispose() {
        log.info("Archangel -> Disposed!");
    }
}
