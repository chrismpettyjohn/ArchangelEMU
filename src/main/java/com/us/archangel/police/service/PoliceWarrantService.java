package com.us.archangel.police.service;

import com.us.archangel.police.entity.PoliceCrimeEntity;
import com.us.archangel.police.mapper.PoliceCrimeMapper;
import com.us.archangel.police.context.PoliceWarrantContext;
import com.us.archangel.police.model.PoliceWarrantModel;
import com.us.archangel.police.repository.PoliceWarrantRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class PoliceWarrantService extends GenericService<PoliceWarrantModel, PoliceWarrantContext, PoliceWarrantRepository> {

    private static PoliceWarrantService instance;

    public static synchronized PoliceWarrantService getInstance() {
        if (instance == null) {
            instance = new PoliceWarrantService();
        }
        return instance;
    }

    private PoliceWarrantService() {
        super(PoliceWarrantContext.getInstance(), PoliceWarrantRepository.getInstance(), PoliceCrimeMapper.class, PoliceCrimeEntity.class);
    }

    public PoliceWarrantModel create(PoliceWarrantModel model) {
        return super.create(model);
    }

    public void update(int id, PoliceWarrantModel model) {
        super.update(id, model);
    }

    public List<PoliceWarrantModel> getAll() {
        return super.getAll();
    }

    public PoliceWarrantModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
