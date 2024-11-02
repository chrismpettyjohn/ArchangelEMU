package com.us.archangel.player.repository;

import com.us.archangel.player.entity.PlayerBankAccountEntity;
import com.us.nova.core.GenericRepository;
import org.hibernate.Session;

public class PlayerBankAccountRepository extends GenericRepository<PlayerBankAccountEntity> {

    private static PlayerBankAccountRepository instance;

    public static synchronized PlayerBankAccountRepository getInstance() {
        if (instance == null) {
            instance = new PlayerBankAccountRepository();
        }
        return instance;
    }

    private PlayerBankAccountRepository() {
        super(PlayerBankAccountEntity.class);
    }

    public PlayerBankAccountEntity getByUserIdAndCorpId(int userId, int corpId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM PlayerBankAccountEntity WHERE userId = :userId AND corpId = :corpId";
            return session.createQuery(hql, PlayerBankAccountEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("corpId", corpId)
                    .uniqueResult();
        }
    }
}
