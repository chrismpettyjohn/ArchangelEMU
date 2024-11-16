package com.us.archangel.police.service;

import com.us.archangel.police.context.PoliceCrimeContext;
import com.us.archangel.police.entity.PoliceCrimeEntity;
import com.us.archangel.police.mapper.PoliceCrimeMapper;
import com.us.archangel.police.model.PoliceCrimeModel;
import com.us.archangel.police.repository.PoliceCrimeRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class PoliceCrimeService extends GenericService<PoliceCrimeModel, PoliceCrimeContext, PoliceCrimeRepository> {

    private static PoliceCrimeService instance;

    public static synchronized PoliceCrimeService getInstance() {
        if (instance == null) {
            instance = new PoliceCrimeService();
        }
        return instance;
    }

    private PoliceCrimeService() {
        super(PoliceCrimeContext.getInstance(), PoliceCrimeRepository.getInstance(), PoliceCrimeMapper.class, PoliceCrimeEntity.class);
    }

    public PoliceCrimeModel create(PoliceCrimeModel model) {
        return super.create(model);
    }

    public void update(int id, PoliceCrimeModel model) {
        super.update(id, model);
    }

    public List<PoliceCrimeModel> getAll() {
        return super.getAll();
    }

    public PoliceCrimeModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
