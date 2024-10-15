package com.us.archangel.player;

import com.us.archangel.player.context.PlayerContext;
import com.us.archangel.player.context.PlayerSkillContext;
import com.us.archangel.player.entity.PlayerEntity;
import com.us.archangel.player.mapper.PlayerMapper;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.repository.PlayerRepository;
import com.us.archangel.player.repository.PlayerSkillRepository;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class PlayerManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerManager.class);

    private static PlayerManager instance;

    public static PlayerManager getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new PlayerManager(sessionFactory);
        }
        return instance;
    }

    public static PlayerManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("PlayerManager has not been initialized");
        }
        return instance;
    }

    private final PlayerRepository playerRepository;

    private final PlayerSkillRepository playerSkillRepository;

    private final PlayerContext playerContext;

    private final PlayerSkillContext playerSkillContext;


    private PlayerManager(SessionFactory sessionFactory) {
        this.playerContext = PlayerContext.getInstance();
        this.playerRepository = PlayerRepository.getInstance(sessionFactory);

        this.playerSkillContext = PlayerSkillContext.getInstance();
        this.playerSkillRepository = PlayerSkillRepository.getInstance(sessionFactory);
        this.load();
    }

    public void load() {
        LOGGER.info("Player manager > starting");

        List<PlayerEntity> playerEntities = PlayerRepository.getInstance().getAll();

        for (PlayerEntity playerEntity : playerEntities) {
            this.playerContext.add(playerEntity.getId(), PlayerMapper.toModel(playerEntity));
        }

        LOGGER.info("Player manager > loaded " + playerEntities.size() + " players");
    }

    public void dispose() {
        for (PlayerModel playerModel : this.playerContext.getAll().values()) {
            this.playerRepository.updateById(playerModel.getId(), PlayerMapper.toEntity(playerModel));
            this.playerContext.delete(playerModel.getId());
        }
        LOGGER.info("Player manager > disposed");
    }
}
