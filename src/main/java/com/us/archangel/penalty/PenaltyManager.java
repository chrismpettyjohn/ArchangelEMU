package com.us.archangel.penalty;

import com.us.archangel.penalty.context.PenaltyContext;
import com.us.archangel.penalty.entity.PenaltyEntity;
import com.us.archangel.penalty.mapper.PenaltyMapper;
import com.us.archangel.penalty.model.PenaltyModel;
import com.us.archangel.penalty.repository.PenaltyRepository;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class PenaltyManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PenaltyManager.class);

    private static PenaltyManager instance;

    public static PenaltyManager getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new PenaltyManager(sessionFactory);
        }
        return instance;
    }

    public static PenaltyManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("PenaltyManager has not been initialized");
        }
        return instance;
    }

    private final PenaltyRepository penaltyRepository;

    private final PenaltyContext penaltyContext;

    private PenaltyManager(SessionFactory sessionFactory) {
        this.penaltyContext = PenaltyContext.getInstance();
        this.penaltyRepository = PenaltyRepository.getInstance(sessionFactory);
        this.load();
    }

    public void load() {
        LOGGER.info("Penalty manager > starting");

        List<PenaltyEntity> penaltyEntities = PenaltyRepository.getInstance().getAll();

        for (PenaltyEntity penaltyEntity : penaltyEntities) {
            this.penaltyContext.add(penaltyEntity.getId(), PenaltyMapper.toModel(penaltyEntity));
        }

        LOGGER.info("Penalty manager > loaded " + penaltyEntities.size() + " penalties");
    }

    public void dispose() {
        for (PenaltyModel penaltyModel : this.penaltyContext.getAll().values()) {
            this.penaltyRepository.updateById(penaltyModel.getId(), PenaltyMapper.toEntity(penaltyModel));
            this.penaltyContext.delete(penaltyModel.getId());
        }
        LOGGER.info("Penalty manager > disposed");
    }
}
