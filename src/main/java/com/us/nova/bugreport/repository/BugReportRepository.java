package com.us.nova.bugreport.repository;

import com.us.nova.bugreport.entity.BugReportEntity;
import com.us.nova.core.GenericRepository;

import java.util.List;

public class BugReportRepository extends GenericRepository<BugReportEntity> {

    private static BugReportRepository instance;

    public static BugReportRepository getInstance() {
        if (instance == null) {
            instance = new BugReportRepository();
        }
        return instance;
    }

    private BugReportRepository() {
        super(BugReportEntity.class);
    }

    public void create(BugReportEntity entity) {
        super.create(entity);
    }

    public void updateById(int id, BugReportEntity entity) {
        super.updateById(id, entity);
    }

    public BugReportEntity getById(int id) {
        return super.getById(id);
    }

    public List<BugReportEntity> getAll() {
        return super.getAll();
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
