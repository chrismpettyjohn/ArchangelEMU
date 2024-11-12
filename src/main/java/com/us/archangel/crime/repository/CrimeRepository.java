package com.us.archangel.crime.repository;

import com.us.archangel.crime.entity.CrimeEntity;
import com.us.nova.core.GenericRepository;

import java.util.List;

public class CrimeRepository extends GenericRepository<CrimeEntity> {

    private static CrimeRepository instance;

    public static CrimeRepository getInstance() {
        if (instance == null) {
            instance = new CrimeRepository();
        }
        return instance;
    }
    private CrimeRepository() {
        super(CrimeEntity.class); // Pass entity type to GenericRepository
    }

    public CrimeEntity create(CrimeEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, CrimeEntity entity) {
        super.updateById(id, entity);
    }

    public CrimeEntity getById(int id) {
        return super.getById(id);
    }

    public List<CrimeEntity> getAll() {
        return super.getAll();
    }
}
