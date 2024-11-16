package com.us.archangel.police.repository;

import com.us.archangel.police.entity.PoliceCrimeEntity;
import com.us.nova.core.GenericRepository;

import java.util.List;

public class PoliceCrimeRepository extends GenericRepository<PoliceCrimeEntity> {

    private static PoliceCrimeRepository instance;

    public static PoliceCrimeRepository getInstance() {
        if (instance == null) {
            instance = new PoliceCrimeRepository();
        }
        return instance;
    }
    private PoliceCrimeRepository() {
        super(PoliceCrimeEntity.class); // Pass entity type to GenericRepository
    }

    public PoliceCrimeEntity create(PoliceCrimeEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, PoliceCrimeEntity entity) {
        super.updateById(id, entity);
    }

    public PoliceCrimeEntity getById(int id) {
        return super.getById(id);
    }

    public List<PoliceCrimeEntity> getAll() {
        return super.getAll();
    }
}
