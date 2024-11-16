package com.us.archangel.police.repository;

import com.us.archangel.police.entity.PoliceWarrantEntity;
import com.us.nova.core.GenericRepository;

import java.util.List;

public class PoliceWarrantRepository extends GenericRepository<PoliceWarrantEntity> {

    private static PoliceWarrantRepository instance;

    public static PoliceWarrantRepository getInstance() {
        if (instance == null) {
            instance = new PoliceWarrantRepository();
        }
        return instance;
    }
    private PoliceWarrantRepository() {
        super(PoliceWarrantEntity.class);
    }

    public PoliceWarrantEntity create(PoliceWarrantEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, PoliceWarrantEntity entity) {
        super.updateById(id, entity);
    }

    public PoliceWarrantEntity getById(int id) {
        return super.getById(id);
    }

    public List<PoliceWarrantEntity> getAll() {
        return super.getAll();
    }
}
