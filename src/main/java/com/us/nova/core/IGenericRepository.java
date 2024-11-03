package com.us.nova.core;

import java.util.List;

public interface IGenericRepository<Entity> {

    void create(Entity entity);

    void updateById(int id, Entity updatedEntity);

    Entity getById(int id);

    List<Entity> getAll();

    void deleteById(int id);
}
