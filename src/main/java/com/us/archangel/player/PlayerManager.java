package com.us.archangel.player;

import com.us.archangel.player.context.*;
import com.us.archangel.player.entity.PlayerEntity;
import com.us.archangel.player.mapper.*;
import com.us.archangel.player.model.*;
import com.us.archangel.player.repository.*;
import com.us.archangel.player.service.*;
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

    private final PlayerAmmoContext playerAmmoContext;
    private final PlayerAmmoRepository playerAmmoRepository;
    private final PlayerAmmoService playerAmmoService;

    private final PlayerSkillContext playerSkillContext;
    private final PlayerSkillRepository playerSkillRepository;
    private final PlayerSkillService playerSkillService;

    private final PlayerWeaponContext playerWeaponContext;
    private final PlayerWeaponRepository playerWeaponRepository;
    private final PlayerWeaponService playerWeaponService;

    private final PlayerBankAccountContext playerBankAccountContext;
    private final PlayerBankAccountRepository playerBankAccountRepository;
    private final PlayerBankAccountService playerBankAccountService;

    private final PlayerKillHistoryContext playerKillHistoryContext;
    private final PlayerKillHistoryRepository playerKillHistoryRepository;
    private final PlayerKillHistoryService playerKillHistoryService;

    private PlayerManager() {
        this.playerContext = PlayerContext.getInstance();
        this.playerRepository = PlayerRepository.getInstance();
        this.playerService = PlayerService.getInstance();


        this.playerAmmoContext = PlayerAmmoContext.getInstance();
        this.playerAmmoRepository = PlayerAmmoRepository.getInstance();
        this.playerAmmoService = PlayerAmmoService.getInstance();

        this.playerSkillContext = PlayerSkillContext.getInstance();
        this.playerSkillRepository = PlayerSkillRepository.getInstance();
        this.playerSkillService = PlayerSkillService.getInstance();

        this.playerWeaponContext = PlayerWeaponContext.getInstance();
        this.playerWeaponRepository = PlayerWeaponRepository.getInstance();
        this.playerWeaponService = PlayerWeaponService.getInstance();

        this.playerBankAccountContext = PlayerBankAccountContext.getInstance();
        this.playerBankAccountRepository = PlayerBankAccountRepository.getInstance();
        this.playerBankAccountService = PlayerBankAccountService.getInstance();

        this.playerKillHistoryContext = PlayerKillHistoryContext.getInstance();
        this.playerKillHistoryRepository = PlayerKillHistoryRepository.getInstance();
        this.playerKillHistoryService = PlayerKillHistoryService.getInstance();

        this.load();
    }

    public void load() {
        LOGGER.info("Player manager > starting");

        this.playerService.getAll();
        this.playerAmmoService.getAll();
        this.playerWeaponService.getAll();
        this.playerBankAccountService.getAll();
        this.playerKillHistoryService.getAll();

        LOGGER.info("Player manager > loaded");
    }

    public void dispose() {
        for (PlayerModel playerModel : this.playerContext.getAll().values()) {
            this.playerRepository.updateById(playerModel.getId(), PlayerMapper.toEntity(playerModel));
            this.playerContext.delete(playerModel.getId());
        }
        for (PlayerAmmoModel playerAmmoModel : this.playerAmmoContext.getAll().values()) {
            this.playerAmmoRepository.updateById(playerAmmoModel.getId(), PlayerAmmoMapper.toEntity(playerAmmoModel));
            this.playerAmmoContext.delete(playerAmmoModel.getId());
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

        for (PlayerKillHistoryModel playerKillHistoryModel : this.playerKillHistoryContext.getAll().values()) {
            this.playerBankAccountRepository.updateById(playerKillHistoryModel.getId(), PlayerBankAccountMapper.toEntity(playerKillHistoryModel));
            this.playerBankAccountContext.delete(playerKillHistoryModel.getId());
        }

        LOGGER.info("Player manager > disposed");
    }
}
