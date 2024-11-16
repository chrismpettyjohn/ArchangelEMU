package com.us.archangel.government.service;

import com.us.archangel.government.context.GovernmentLawContext;
import com.us.archangel.government.entity.GovernmentLawEntity;
import com.us.archangel.government.mapper.GovernmentLawMapper;
import com.us.archangel.government.model.GovernmentLawModel;
import com.us.archangel.government.repository.GovernmentLawRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class GovernmentLawService extends GenericService<GovernmentLawModel, GovernmentLawContext, GovernmentLawRepository> {

    private static GovernmentLawService instance;

    public static synchronized GovernmentLawService getInstance() {
        if (instance == null) {
            instance = new GovernmentLawService();
        }
        return instance;
    }

    private GovernmentLawService() {
        super(GovernmentLawContext.getInstance(), GovernmentLawRepository.getInstance(), GovernmentLawMapper.class, GovernmentLawEntity.class);
    }

    public GovernmentLawModel create(GovernmentLawModel model) {
        return super.create(model);
    }

    public void update(int id, GovernmentLawModel model) {
        super.update(id, model);
    }

    public List<GovernmentLawModel> getAll() {
        return super.getAll();
    }

    public GovernmentLawModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
