package com.us.nova.bugreport;


import com.us.nova.bugreport.context.BugReportContext;
import com.us.nova.bugreport.mapper.BugReportMapper;
import com.us.nova.bugreport.model.BugReportModel;
import com.us.nova.bugreport.repository.BugReportRepository;
import com.us.nova.bugreport.service.BugReportService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class BugReportManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(BugReportManager.class);

    private static BugReportManager instance;

    public static BugReportManager getInstance() {
        if (instance == null) {
            instance = new BugReportManager();
        }
        return instance;
    }

    private final BugReportRepository bugReportRepository;
    private final BugReportContext bugReportContext;
    private final BugReportService bugReportService;

    private BugReportManager() {
        this.bugReportContext = BugReportContext.getInstance();
        this.bugReportRepository = BugReportRepository.getInstance();
        this.bugReportService = BugReportService.getInstance();
        this.load();
    }

    public void load() {
        LOGGER.info("Bug Report manager > starting");

        this.bugReportService.getAll();

        LOGGER.info("Bug Report manager > loaded");
    }

    public void dispose() {
        for (BugReportModel bugReportModel : this.bugReportContext.getAll().values()) {
            this.bugReportRepository.updateById(bugReportModel.getId(), BugReportMapper.toEntity(bugReportModel));
            this.bugReportContext.delete(bugReportModel.getId());
        }
        LOGGER.info("Bug Report manager > disposed");
    }
}
