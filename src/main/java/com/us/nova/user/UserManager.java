package com.us.nova.user;

import com.us.nova.user.context.UserContext;
import com.us.nova.user.context.UserSSOContext;
import com.us.nova.user.entity.UserEntity;
import com.us.nova.user.mapper.UserMapper;
import com.us.nova.user.mapper.UserSSOMapper;
import com.us.nova.user.model.UserModel;
import com.us.nova.user.model.UserSSOModel;
import com.us.nova.user.repository.UserRepository;
import com.us.nova.user.repository.UserSSORepository;
import com.us.nova.user.service.UserSSOService;
import com.us.nova.user.service.UserService;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class UserManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

    private static UserManager instance;

    public static UserManager getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new UserManager(sessionFactory);
        }
        return instance;
    }

    public static UserManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("UserManager has not been initialized");
        }
        return instance;
    }

    private final UserRepository userRepository;
    private final UserContext userContext;
    private final UserService userService;

    private final UserSSORepository userSSORepository;
    private final UserSSOContext userSSOContext;
    private final UserSSOService userSSOService;

    private UserManager(SessionFactory sessionFactory) {
        this.userContext = UserContext.getInstance();
        this.userRepository = UserRepository.getInstance(sessionFactory);
        this.userService = UserService.getInstance();

        this.userSSOContext = UserSSOContext.getInstance();
        this.userSSORepository = UserSSORepository.getInstance(sessionFactory);
        this.userSSOService = UserSSOService.getInstance();
    }

    public void dispose() {
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
