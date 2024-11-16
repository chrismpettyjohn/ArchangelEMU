package com.us.archangel.police;

import com.us.archangel.government.mapper.GovernmentLawMapper;
import com.us.archangel.police.context.PoliceCrimeContext;
import com.us.archangel.police.context.PoliceReportContext;
import com.us.archangel.police.context.PoliceWarrantContext;
import com.us.archangel.police.model.PoliceCrimeModel;
import com.us.archangel.police.model.PoliceReportModel;
import com.us.archangel.police.model.PoliceWarrantModel;
import com.us.archangel.police.repository.PoliceCrimeRepository;
import com.us.archangel.police.repository.PoliceReportRepository;
import com.us.archangel.police.repository.PoliceWarrantRepository;
import com.us.archangel.police.service.PoliceCrimeService;
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

    private final PoliceCrimeRepository policeCrimeRepository;
    private final PoliceCrimeContext policeCrimeContext;
    private final PoliceCrimeService policeCrimeService;

    private final PoliceReportRepository policeReportRepository;
    private final PoliceReportContext policeReportContext;
    private final PoliceReportService policeReportService;

    private final PoliceWarrantRepository policeWarrantRepository;
    private final PoliceWarrantContext policeWarrantContext;
    private final PoliceWarrantService policeWarrantService;

    private PoliceManager() {
        LOGGER.info("Police manager > starting");


        this.policeCrimeContext = PoliceCrimeContext.getInstance();
        this.policeCrimeRepository = PoliceCrimeRepository.getInstance();
        this.policeCrimeService = PoliceCrimeService.getInstance();

        this.policeReportContext = PoliceReportContext.getInstance();
        this.policeReportRepository = PoliceReportRepository.getInstance();
        this.policeReportService = PoliceReportService.getInstance();

        this.policeWarrantContext = PoliceWarrantContext.getInstance();
        this.policeWarrantRepository = PoliceWarrantRepository.getInstance();
        this.policeWarrantService = PoliceWarrantService.getInstance();

        LOGGER.info("Police manager > loaded");
    }

    public void dispose() {
        for (PoliceCrimeModel crimeModel : this.policeCrimeContext.getAll().values()) {
            this.policeCrimeRepository.updateById(crimeModel.getId(), GovernmentLawMapper.toEntity(crimeModel));
            this.policeCrimeContext.delete(crimeModel.getId());
        }

        for (PoliceReportModel reportModel : this.policeReportContext.getAll().values()) {
            this.policeReportRepository.updateById(reportModel.getId(), GovernmentLawMapper.toEntity(reportModel));
            this.policeReportContext.delete(reportModel.getId());
        }

        for (PoliceWarrantModel warrantModel : this.policeWarrantContext.getAll().values()) {
            this.policeWarrantRepository.updateById(warrantModel.getId(), GovernmentLawMapper.toEntity(warrantModel));
            this.policeWarrantContext.delete(warrantModel.getId());
        }

        LOGGER.info("Government manager > disposed");
    }
}
