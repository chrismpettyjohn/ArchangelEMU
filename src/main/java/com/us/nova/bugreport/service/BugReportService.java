package com.us.nova.bugreport.service;

import com.us.nova.bugreport.context.BugReportContext;
import com.us.nova.bugreport.entity.BugReportEntity;
import com.us.nova.bugreport.mapper.BugReportMapper;
import com.us.nova.bugreport.model.BugReportModel;
import com.us.nova.bugreport.repository.BugReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BugReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BugReportService.class);

    private static BugReportService instance;

    public static BugReportService getInstance() {
        if (instance == null) {
            instance = new BugReportService();
        }
        return instance;
    }

    private BugReportService() {
        LOGGER.info("Bug Report Service > starting");
        this.getAll();
        LOGGER.info("Bug Report Service > loaded {} bug reports", this.getAll().size());
    }

    public void create(BugReportEntity bugReportEntity) {
        BugReportContext.getInstance().add(bugReportEntity.getId(), BugReportMapper.toModel(bugReportEntity));
        BugReportRepository.getInstance().create(bugReportEntity);
    }

    public void update(int id, BugReportEntity updatedBugReport) {
        BugReportContext.getInstance().update(id, BugReportMapper.toModel(updatedBugReport));
        BugReportRepository.getInstance().updateById(id, updatedBugReport);
    }
    
    public List<BugReportModel> getAll() {
        Map<Integer, BugReportModel> models = BugReportContext.getInstance().getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<BugReportEntity> entities = BugReportRepository.getInstance().getAll();
        List<BugReportModel> modelList = entities.stream()
                .map(BugReportMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> BugReportContext.getInstance().add(model.getId(), model));
        return modelList;
    }


    public void deleteById(int id) {
        BugReportContext.getInstance().delete(id);
        BugReportRepository.getInstance().deleteById(id);
    }
}
