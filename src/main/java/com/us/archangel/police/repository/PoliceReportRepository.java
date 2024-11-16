package com.us.archangel.police.repository;

import com.us.archangel.police.entity.PoliceReportEntity;
import com.us.nova.core.GenericRepository;

import java.util.List;

public class PoliceReportRepository extends GenericRepository<PoliceReportEntity> {

    private static PoliceReportRepository instance;

    public static PoliceReportRepository getInstance() {
        if (instance == null) {
            instance = new PoliceReportRepository();
        }
        return instance;
    }
    private PoliceReportRepository() {
        super(PoliceReportEntity.class);
    }

    public PoliceReportEntity create(PoliceReportEntity entity) {
        return super.create(entity);
    }

    public void updateById(int id, PoliceReportEntity entity) {
        super.updateById(id, entity);
    }

    public PoliceReportEntity getById(int id) {
        return super.getById(id);
    }

    public List<PoliceReportEntity> getAll() {
        return super.getAll();
    }
}
