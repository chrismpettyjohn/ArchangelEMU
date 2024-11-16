package com.us.archangel.police.service;

import com.us.archangel.police.context.PoliceReportContext;
import com.us.archangel.police.entity.PoliceReportEntity;
import com.us.archangel.police.mapper.PoliceReportMapper;
import com.us.archangel.police.model.PoliceReportModel;
import com.us.archangel.police.repository.PoliceReportRepository;
import com.us.nova.core.GenericService;

import java.util.List;

public class PoliceReportService extends GenericService<PoliceReportModel, PoliceReportContext, PoliceReportRepository> {

    private static PoliceReportService instance;

    public static synchronized PoliceReportService getInstance() {
        if (instance == null) {
            instance = new PoliceReportService();
        }
        return instance;
    }

    private PoliceReportService() {
        super(PoliceReportContext.getInstance(), PoliceReportRepository.getInstance(), PoliceReportMapper.class, PoliceReportEntity.class);
    }

    public PoliceReportModel create(PoliceReportModel model) {
        return super.create(model);
    }

    public void update(int id, PoliceReportModel model) {
        super.update(id, model);
    }

    public List<PoliceReportModel> getAll() {
        return super.getAll();
    }

    public PoliceReportModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
