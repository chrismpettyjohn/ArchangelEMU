package com.us.archangel.crime.service;

import com.us.archangel.crime.context.CrimeContext;
import com.us.archangel.crime.entity.CrimeEntity;
import com.us.archangel.crime.mapper.CrimeMapper;
import com.us.archangel.crime.model.CrimeModel;
import com.us.archangel.crime.repository.CrimeRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class CrimeService extends GenericService<CrimeModel, CrimeContext, CrimeRepository> {

    private static CrimeService instance;

    public static synchronized CrimeService getInstance() {
        if (instance == null) {
            instance = new CrimeService();
        }
        return instance;
    }

    private CrimeService() {
        super(CrimeContext.getInstance(), CrimeRepository.getInstance(), CrimeMapper.class, CrimeEntity.class);
    }

    public void create(CrimeModel model) {
        super.create(model);
    }

    public void update(int id, CrimeModel model) {
        super.update(id, model);
    }

    public List<CrimeModel> getAll() {
        return super.getAll();
    }

    public CrimeModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
