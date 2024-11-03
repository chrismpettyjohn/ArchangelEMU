package com.us.nova.betacode;


import com.us.nova.betacode.context.BetaCodeContext;
import com.us.nova.betacode.mapper.BetaCodeMapper;
import com.us.nova.betacode.model.BetaCodeModel;
import com.us.nova.betacode.repository.BetaCodeRepository;
import com.us.nova.betacode.service.BetaCodeService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class BetaCodeManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(BetaCodeManager.class);

    private static BetaCodeManager instance;

    public static BetaCodeManager getInstance() {
        if (instance == null) {
            instance = new BetaCodeManager();
        }
        return instance;
    }

    private final BetaCodeRepository betaCodeRepository;
    private final BetaCodeContext betaCodeContext;
    private final BetaCodeService betaCodeService;

    private BetaCodeManager() {
        this.betaCodeContext = BetaCodeContext.getInstance();
        this.betaCodeRepository = BetaCodeRepository.getInstance();
        this.betaCodeService = BetaCodeService.getInstance();
    }

    public void dispose() {
        for (BetaCodeModel betaCodeModel : this.betaCodeContext.getAll().values()) {
            this.betaCodeRepository.updateById(betaCodeModel.getId(), BetaCodeMapper.toEntity(betaCodeModel));
            this.betaCodeContext.delete(betaCodeModel.getId());
        }
        LOGGER.info("Beta Code manager > disposed");
    }
}
