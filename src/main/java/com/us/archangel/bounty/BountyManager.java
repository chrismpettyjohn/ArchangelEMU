package com.us.archangel.bounty;

import com.us.archangel.bounty.context.BountyContext;
import com.us.archangel.bounty.entity.BountyEntity;
import com.us.archangel.bounty.mapper.BountyMapper;
import com.us.archangel.bounty.model.BountyModel;
import com.us.archangel.bounty.repository.BountyRepository;
import com.us.archangel.bounty.service.BountyService;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class BountyManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(BountyManager.class);

    private static BountyManager instance;

    public static BountyManager getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new BountyManager(sessionFactory);
        }
        return instance;
    }

    public static BountyManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("BountyManager has not been initialized");
        }
        return instance;
    }

    private final BountyRepository bountyRepository;
    private final BountyContext bountyContext;
    private final BountyService bountyService;

    private BountyManager(SessionFactory sessionFactory) {
        this.bountyContext = BountyContext.getInstance();
        this.bountyRepository = BountyRepository.getInstance(sessionFactory);
        this.bountyService = BountyService.getInstance();
        this.load();
    }

    public void load() {
        LOGGER.info("Bounty manager > starting");

        List<BountyEntity> entities = BountyRepository.getInstance().getAll();

        for (BountyEntity entity : entities) {
            this.bountyContext.add(entity.getId(), BountyMapper.toModel(entity));
        }

        LOGGER.info("Bounty manager > loaded " + entities.size() + " bounties");
    }

    public void dispose() {
        for (BountyModel bountyModel : this.bountyContext.getAll().values()) {
            this.bountyRepository.updateById(bountyModel.getId(), BountyMapper.toEntity(bountyModel));
            this.bountyContext.delete(bountyModel.getId());
        }
        LOGGER.info("Bounty manager > disposed");
    }
}
