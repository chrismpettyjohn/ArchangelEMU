package com.us.archangel.core;

import com.us.archangel.bounty.entity.BountyEntity;
import com.us.archangel.corp.entity.CorpEntity;
import com.us.archangel.corp.entity.CorpInviteEntity;
import com.us.archangel.corp.entity.CorpRoleEntity;
import com.us.archangel.gang.entity.GangEntity;
import com.us.archangel.gang.entity.GangRoleEntity;
import com.us.archangel.player.entity.PlayerEntity;
import com.us.archangel.player.entity.PlayerSkillEntity;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.sanction.entity.SanctionEntity;
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
                configuration.setProperty("jakarta.persistence.lock.timeout", "5000");
                configuration.setProperty("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform");

                configuration.setProperty("hibernate.show_sql", "false");
                configuration.setProperty("hibernate.format_sql", "false");
                configuration.setProperty("hibernate.use_sql_comments", "false");
                configuration.setProperty("hibernate.generate_statistics", "false");


                configuration.addAnnotatedClass(BountyEntity.class);

                configuration.addAnnotatedClass(CorpEntity.class);
                configuration.addAnnotatedClass(CorpRoleEntity.class);
                configuration.addAnnotatedClass(CorpInviteEntity.class);

                configuration.addAnnotatedClass(GangEntity.class);
                configuration.addAnnotatedClass(GangRoleEntity.class);

                configuration.addAnnotatedClass(PlayerEntity.class);
                configuration.addAnnotatedClass(PlayerSkillEntity.class);
                configuration.addAnnotatedClass(PlayerWeaponModel.class);

                configuration.addAnnotatedClass(SanctionEntity.class);

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
