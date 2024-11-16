package com.us.archangel.police;

import com.us.archangel.police.context.PoliceReportContext;
import com.us.archangel.police.context.PoliceWarrantContext;
import com.us.archangel.police.repository.PoliceReportRepository;
import com.us.archangel.police.repository.PoliceWarrantRepository;
import com.us.archangel.police.service.PoliceReportService;
import com.us.archangel.police.service.PoliceWarrantService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class PoliceManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoliceManager.class);

    private static PoliceManager instance;

    public static PoliceManager getInstance() {
        if (instance == null) {
            instance = new PoliceManager();
        }
        return instance;
    }

    private final PoliceReportRepository policeReportRepository;
    private final PoliceReportContext policeReportContext;
    private final PoliceReportService policeReportService;

    private final PoliceWarrantRepository policeWarrantRepository;
    private final PoliceWarrantContext policeWarrantContext;
    private final PoliceWarrantService policeWarrantService;

    private PoliceManager() {
        LOGGER.info("Police manager > starting");

        this.policeReportContext = PoliceReportContext.getInstance();
        this.policeReportRepository = PoliceReportRepository.getInstance();
        this.policeReportService = PoliceReportService.getInstance();

        this.policeWarrantContext = PoliceWarrantContext.getInstance();
        this.policeWarrantRepository = PoliceWarrantRepository.getInstance();
        this.policeWarrantService = PoliceWarrantService.getInstance();

        LOGGER.info("Police manager > loaded");
    }
}
