package com.us.nova.betacode.repository;

import com.us.nova.betacode.entity.BetaCodeEntity;
import com.us.nova.core.GenericRepository;

import java.util.List;

public class BetaCodeRepository extends GenericRepository<BetaCodeEntity> {

    private static BetaCodeRepository instance;

    public static BetaCodeRepository getInstance() {
        if (instance == null) {
            instance = new BetaCodeRepository();
        }
        return instance;
    }

    private BetaCodeRepository() {
        super(BetaCodeEntity.class);
    }

    public BetaCodeEntity create(BetaCodeEntity betaCode) {
        return super.create(betaCode);
    }

    public void updateById(int id, BetaCodeEntity updatedBetaCode) {
        super.updateById(id, updatedBetaCode);
    }

    public BetaCodeEntity getById(int id) {
        return super.getById(id);
    }

    public List<BetaCodeEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
