package com.us.nova.betacode.service;

import com.us.nova.betacode.context.BetaCodeContext;
import com.us.nova.betacode.entity.BetaCodeEntity;
import com.us.nova.betacode.mapper.BetaCodeMapper;
import com.us.nova.betacode.model.BetaCodeModel;
import com.us.nova.betacode.repository.BetaCodeRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class BetaCodeService extends GenericService<BetaCodeModel, BetaCodeContext, BetaCodeRepository> {

    private static BetaCodeService instance;

    public static BetaCodeService getInstance() {
        if (instance == null) {
            instance = new BetaCodeService();
        }
        return instance;
    }

    private BetaCodeService() {
        super(BetaCodeContext.getInstance(), BetaCodeRepository.getInstance(), BetaCodeMapper.class, BetaCodeEntity.class);
    }

    public BetaCodeModel create(BetaCodeModel model) {
       return super.create(model);
    }

    public void update(int id, BetaCodeModel model) {
        super.update(id, model);
    }

    public List<BetaCodeModel> getAll() {
        return super.getAll();
    }

    public BetaCodeModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
