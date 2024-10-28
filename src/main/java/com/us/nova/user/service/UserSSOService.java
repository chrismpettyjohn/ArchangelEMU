package com.us.nova.user.service;

import com.us.nova.user.context.UserSSOContext;
import com.us.nova.user.entity.UserSSOEntity;
import com.us.nova.user.mapper.UserSSOMapper;
import com.us.nova.user.model.UserSSOModel;
import com.us.nova.user.repository.UserSSORepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserSSOService {

    private static UserSSOService instance;


    public static UserSSOService getInstance() {
        if (instance == null) {
            instance = new UserSSOService();
        }
        return instance;
    }

    public void create(UserSSOEntity userSSOEntity) {
        UserSSOContext.getInstance().add(userSSOEntity.getId(), UserSSOMapper.toModel(userSSOEntity));
        UserSSORepository.getInstance().create(userSSOEntity);
    }

    public void update(int id, UserSSOEntity userSSOEntity) {
        UserSSOContext.getInstance().update(id, UserSSOMapper.toModel(userSSOEntity));
        UserSSORepository.getInstance().updateById(id, userSSOEntity);
    }
    
    public List<UserSSOModel> getAll() {
        Map<Integer, UserSSOModel> models = UserSSOContext.getInstance().getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<UserSSOEntity> entities = UserSSORepository.getInstance().getAll();
        List<UserSSOModel> modelList = entities.stream()
                .map(UserSSOMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> UserSSOContext.getInstance().add(model.getId(), model));
        return modelList;
    }


    public void deleteById(int id) {
        UserSSOContext.getInstance().delete(id);
        UserSSORepository.getInstance().deleteById(id);
    }
}
