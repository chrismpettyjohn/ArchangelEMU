package com.us.archangel.bounty;

import com.us.archangel.bounty.context.BountyContext;
import com.us.archangel.bounty.mapper.BountyMapper;
import com.us.archangel.bounty.model.BountyModel;
import com.us.archangel.bounty.repository.BountyRepository;
import com.us.archangel.bounty.service.BountyService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class BountyManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(BountyManager.class);

    private static BountyManager instance;

    public static BountyManager getInstance() {
        if (instance == null) {
            instance = new BountyManager();
        }
        return instance;
    }
    private final BountyRepository bountyRepository;
    private final BountyContext bountyContext;
    private final BountyService bountyService;

    private BountyManager() {
        this.bountyContext = BountyContext.getInstance();
        this.bountyRepository = BountyRepository.getInstance();
        this.bountyService = BountyService.getInstance();
        this.load();
    }

    public void load() {
        LOGGER.info("Bounty manager > starting");

        this.bountyService.getAll();

        LOGGER.info("Bounty manager > loaded");
    }

    public void dispose() {
        for (BountyModel bountyModel : this.bountyContext.getAll().values()) {
            this.bountyRepository.updateById(bountyModel.getId(), BountyMapper.toEntity(bountyModel));
            this.bountyContext.delete(bountyModel.getId());
        }
        LOGGER.info("Bounty manager > disposed");
    }
}
