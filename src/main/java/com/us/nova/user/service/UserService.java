package com.us.nova.user.service;

import com.us.nova.user.context.UserContext;
import com.us.nova.user.entity.UserEntity;
import com.us.nova.user.mapper.UserMapper;
import com.us.nova.user.model.UserModel;
import com.us.nova.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {

    private static UserService instance;


    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void create(UserEntity userEntity, UserModel userModel) {
        UserContext.getInstance().add(userEntity.getId(), userModel);
        UserRepository.getInstance().create(userEntity);
    }

    public void update(int id, UserEntity updatedUser) {
        UserContext.getInstance().update(id, UserMapper.toModel(updatedUser));
        UserRepository.getInstance().updateById(id, updatedUser);
    }
    
    public List<UserModel> getAll() {
        Map<Integer, UserModel> models = UserContext.getInstance().getAll();
        if (!models.isEmpty()) {
            return new ArrayList<>(models.values());
        }

        List<UserEntity> entities = UserRepository.getInstance().getAll();
        List<UserModel> modelList = entities.stream()
                .map(UserMapper::toModel)
                .collect(Collectors.toList());

        modelList.forEach(model -> UserContext.getInstance().add(model.getId(), model));
        return modelList;
    }


    public void deleteById(int id) {
        UserContext.getInstance().delete(id);
        UserRepository.getInstance().deleteById(id);
    }
}
