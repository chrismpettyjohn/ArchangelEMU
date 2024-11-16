package com.us.nova.user.service;

import com.us.nova.core.GenericService;
import com.us.nova.user.context.UserGuestbookContext;
import com.us.nova.user.entity.UserGuestbookEntity;
import com.us.nova.user.mapper.UserGuestbookMapper;
import com.us.nova.user.model.UserGuestbookModel;
import com.us.nova.user.repository.UserGuestbookRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserGuestbookService extends GenericService<UserGuestbookModel, UserGuestbookContext, UserGuestbookRepository> {
    private static UserGuestbookService instance;

    public static UserGuestbookService getInstance() {
        if (instance == null) {
            instance = new UserGuestbookService();
        }
        return instance;
    }

    private UserGuestbookService() {
        super(UserGuestbookContext.getInstance(), UserGuestbookRepository.getInstance(), UserGuestbookMapper.class, UserGuestbookEntity.class);
    }

    public UserGuestbookModel create(UserGuestbookModel model) {
        return super.create(model);
    }

    public void update(int id, UserGuestbookModel model) {
        super.update(id, model);
    }

    public List<UserGuestbookModel> getAll() {
        return super.getAll();
    }

    public List<UserGuestbookModel> getByUserId(int userId) {
        List<UserGuestbookModel> storedValues = context.getAll().values().stream()
                .filter(model -> model.getPostedOnUsersId() == userId)
                .collect(Collectors.toList());

        if (!storedValues.isEmpty()) {
            return storedValues;
        }

        List<UserGuestbookEntity> entities = repository.getByUserId(userId);
        List<UserGuestbookModel> models = entities.stream()
                .map(UserGuestbookMapper::toModel)
                .collect(Collectors.toList());

        models.forEach(model -> context.add(model.getId(), model));
        return models;
    }

    public UserGuestbookModel getById(int id) {
        return super.getById(id);
    }

    public void deleteById(int id) {
        super.deleteById(id);
    }
}
