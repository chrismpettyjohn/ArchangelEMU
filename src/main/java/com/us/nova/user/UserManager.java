package com.us.nova.user;

import com.us.nova.user.context.UserContext;
import com.us.nova.user.context.UserGuestbookContext;
import com.us.nova.user.context.UserSSOContext;
import com.us.nova.user.mapper.UserGuestbookMapper;
import com.us.nova.user.mapper.UserMapper;
import com.us.nova.user.mapper.UserSSOMapper;
import com.us.nova.user.model.UserGuestbookModel;
import com.us.nova.user.model.UserModel;
import com.us.nova.user.model.UserSSOModel;
import com.us.nova.user.repository.UserGuestbookRepository;
import com.us.nova.user.repository.UserRepository;
import com.us.nova.user.repository.UserSSORepository;
import com.us.nova.user.service.UserGuestbookService;
import com.us.nova.user.service.UserSSOService;
import com.us.nova.user.service.UserService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class UserManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

    private static UserManager instance;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private final UserRepository userRepository;
    private final UserContext userContext;
    private final UserService userService;

    private final UserGuestbookRepository userGuestbookRepository;
    private final UserGuestbookContext userGuestbookContext;
    private final UserGuestbookService userGuestbookService;


    private final UserSSORepository userSSORepository;
    private final UserSSOContext userSSOContext;
    private final UserSSOService userSSOService;

    private UserManager() {

        this.userContext = UserContext.getInstance();
        this.userRepository = UserRepository.getInstance();
        this.userService = UserService.getInstance();

        this.userGuestbookContext = UserGuestbookContext.getInstance();
        this.userGuestbookRepository = UserGuestbookRepository.getInstance();
        this.userGuestbookService = UserGuestbookService.getInstance();

        this.userSSOContext = UserSSOContext.getInstance();
        this.userSSORepository = UserSSORepository.getInstance();
        this.userSSOService = UserSSOService.getInstance();
        this.load();
    }

    public void load() {
        LOGGER.info("User manager > starting");

        this.userService.getAll();
        this.userGuestbookService.getAll();
        this.userSSOService.getAll();

        LOGGER.info("User manager > loaded");
    }

    public void dispose() {
        for (UserGuestbookModel guestbookModel : this.userGuestbookContext.getAll().values()) {
            this.userGuestbookRepository.updateById(guestbookModel.getId(), UserGuestbookMapper.toEntity(guestbookModel));
            this.userGuestbookContext.delete(guestbookModel.getId());
        }

        for (UserModel userModel : this.userContext.getAll().values()) {
            this.userRepository.updateById(userModel.getId(), UserMapper.toEntity(userModel));
            this.userContext.delete(userModel.getId());
        }

        for (UserSSOModel userSSO : this.userSSOContext.getAll().values()) {
            this.userSSORepository.updateById(userSSO.getId(), UserSSOMapper.toEntity(userSSO));
            this.userSSOContext.delete(userSSO.getId());
        }
        LOGGER.info("User manager > disposed");
    }
}
