package com.us.archangel.player;

import com.us.archangel.player.context.PlayerBankAccountContext;
import com.us.archangel.player.context.PlayerContext;
import com.us.archangel.player.context.PlayerSkillContext;
import com.us.archangel.player.context.PlayerWeaponContext;
import com.us.archangel.player.entity.PlayerEntity;
import com.us.archangel.player.mapper.PlayerBankAccountMapper;
import com.us.archangel.player.mapper.PlayerMapper;
import com.us.archangel.player.mapper.PlayerSkillMapper;
import com.us.archangel.player.mapper.PlayerWeaponMapper;
import com.us.archangel.player.model.PlayerBankAccountModel;
import com.us.archangel.player.model.PlayerModel;
import com.us.archangel.player.model.PlayerSkillModel;
import com.us.archangel.player.model.PlayerWeaponModel;
import com.us.archangel.player.repository.PlayerBankAccountRepository;
import com.us.archangel.player.repository.PlayerRepository;
import com.us.archangel.player.repository.PlayerSkillRepository;
import com.us.archangel.player.repository.PlayerWeaponRepository;
import com.us.archangel.player.service.PlayerBankAccountService;
import com.us.archangel.player.service.PlayerService;
import com.us.archangel.player.service.PlayerSkillService;
import com.us.archangel.player.service.PlayerWeaponService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class PlayerManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerManager.class);

    private static PlayerManager instance;

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

    private final PlayerContext playerContext;
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    private final PlayerSkillContext playerSkillContext;
    private final PlayerSkillRepository playerSkillRepository;
    private final PlayerSkillService playerSkillService;

    private final PlayerWeaponContext playerWeaponContext;
    private final PlayerWeaponRepository playerWeaponRepository;
    private final PlayerWeaponService playerWeaponService;

    private final PlayerBankAccountContext playerBankAccountContext;
    private final PlayerBankAccountRepository playerBankAccountRepository;
    private final PlayerBankAccountService playerBankAccountService;

    private PlayerManager() {
        this.playerContext = PlayerContext.getInstance();
        this.playerRepository = PlayerRepository.getInstance();
        this.playerService = PlayerService.getInstance();

        this.playerSkillContext = PlayerSkillContext.getInstance();
        this.playerSkillRepository = PlayerSkillRepository.getInstance();
        this.playerSkillService = PlayerSkillService.getInstance();

        this.playerWeaponContext = PlayerWeaponContext.getInstance();
        this.playerWeaponRepository = PlayerWeaponRepository.getInstance();
        this.playerWeaponService = PlayerWeaponService.getInstance();

        this.playerBankAccountContext = PlayerBankAccountContext.getInstance();
        this.playerBankAccountRepository = PlayerBankAccountRepository.getInstance();
        this.playerBankAccountService = PlayerBankAccountService.getInstance();

        this.load();
    }

    public void load() {
        LOGGER.info("Player manager > starting");

        List<PlayerEntity> playerEntities = PlayerRepository.getInstance().getAll();

        for (PlayerEntity playerEntity : playerEntities) {
            this.playerContext.add(playerEntity.getId(), PlayerMapper.toModel(playerEntity));
        }

        LOGGER.info("Player manager > loaded");
    }

    public void dispose() {
        for (PlayerModel playerModel : this.playerContext.getAll().values()) {
            this.playerRepository.updateById(playerModel.getId(), PlayerMapper.toEntity(playerModel));
            this.playerContext.delete(playerModel.getId());
        }

        for (PlayerSkillModel playerSkillModel : this.playerSkillContext.getAll().values()) {
            this.playerSkillRepository.updateById(playerSkillModel.getId(), PlayerSkillMapper.toEntity(playerSkillModel));
            this.playerSkillContext.delete(playerSkillModel.getId());
        }

        for (PlayerWeaponModel playerWeaponModel : this.playerWeaponContext.getAll().values()) {
            this.playerWeaponRepository.updateById(playerWeaponModel.getId(), PlayerWeaponMapper.toEntity(playerWeaponModel));
            this.playerWeaponContext.delete(playerWeaponModel.getId());
        }

        for (PlayerBankAccountModel playerBankAccountModel : this.playerBankAccountContext.getAll().values()) {
            this.playerBankAccountRepository.updateById(playerBankAccountModel.getId(), PlayerBankAccountMapper.toEntity(playerBankAccountModel));
            this.playerBankAccountContext.delete(playerBankAccountModel.getId());
        }
        LOGGER.info("Player manager > disposed");
    }
}
