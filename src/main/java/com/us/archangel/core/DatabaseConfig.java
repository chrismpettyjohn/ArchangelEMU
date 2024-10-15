package com.us.archangel.core;

import com.us.archangel.weapon.entity.WeaponEntity;
import com.eu.habbo.core.ConfigurationManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseConfig {

    private static SessionFactory sessionFactory;

    public static synchronized void initialize(ConfigurationManager config) {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                configuration.setProperty("hibernate.connection.url", "jdbc:mariadb://" + config.getValue("db.hostname") + ":" + config.getValue("db.port") + "/" + config.getValue("db.database"));
                configuration.setProperty("hibernate.connection.username", config.getValue("db.username"));
                configuration.setProperty("hibernate.connection.password", config.getValue("db.password"));
                
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");


                configuration.addAnnotatedClass(WeaponEntity.class);

                sessionFactory = configuration.buildSessionFactory();

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error initializing Hibernate session factory", e);
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is not initialized. Call initialize() first.");
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
