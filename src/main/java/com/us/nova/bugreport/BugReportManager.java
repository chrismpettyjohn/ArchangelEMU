package com.us.nova.bugreport;


import com.us.nova.bugreport.context.BugReportContext;
import com.us.nova.bugreport.mapper.BugReportMapper;
import com.us.nova.bugreport.model.BugReportModel;
import com.us.nova.bugreport.repository.BugReportRepository;
import com.us.nova.bugreport.service.BugReportService;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class BugReportManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(BugReportManager.class);

    private static BugReportManager instance;

    public static BugReportManager getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new BugReportManager(sessionFactory);
        }
        return instance;
    }

    public static BugReportManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("Bug Report Manager has not been initialized");
        }
        return instance;
    }

    private final BugReportRepository bugReportRepository;
    private final BugReportContext bugReportContext;
    private final BugReportService bugReportService;

    private BugReportManager(SessionFactory sessionFactory) {
        this.bugReportContext = BugReportContext.getInstance();
        this.bugReportRepository = BugReportRepository.getInstance(sessionFactory);
        this.bugReportService = BugReportService.getInstance();
    }

    public void dispose() {
        for (BugReportModel bugReportModel : this.bugReportContext.getAll().values()) {
            this.bugReportRepository.updateById(bugReportModel.getId(), BugReportMapper.toEntity(bugReportModel));
            this.bugReportContext.delete(bugReportModel.getId());
        }
        LOGGER.info("Bug Report manager > disposed");
    }
}
