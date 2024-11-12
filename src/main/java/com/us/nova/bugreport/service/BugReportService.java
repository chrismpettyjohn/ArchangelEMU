package com.us.nova.bugreport.service;

import com.us.nova.bugreport.context.BugReportContext;
import com.us.nova.bugreport.entity.BugReportEntity;
import com.us.nova.bugreport.mapper.BugReportMapper;
import com.us.nova.bugreport.model.BugReportModel;
import com.us.nova.bugreport.repository.BugReportRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class BugReportService extends GenericService<BugReportModel, BugReportContext, BugReportRepository> {

    private static BugReportService instance;

    public static BugReportService getInstance() {
        if (instance == null) {
            instance = new BugReportService();
        }
        return instance;
    }

    private BugReportService() {
        super(BugReportContext.getInstance(), BugReportRepository.getInstance(), BugReportMapper.class, BugReportEntity.class);
    }

    public BugReportModel create(BugReportModel model) {
        return super.create(model);
    }

    public void update(int id, BugReportModel model) {
        super.update(id, model);
    }
    
    public List<BugReportModel> getAll() {
       return super.getAll();
    }

    public BugReportModel getById(int id) {
       return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
