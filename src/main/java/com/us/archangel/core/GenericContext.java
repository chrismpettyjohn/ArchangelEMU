package com.us.archangel.core;

import java.util.HashMap;
import java.util.Map;

public class GenericContext<T> {

    private static GenericContext<?> instance;
    private final Map<Integer, T> entities = new HashMap<>();

    protected GenericContext() {}

    public static <T> GenericContext<T> getInstance() {
        if (instance == null) {
            instance = new GenericContext<>();
        }
        return (GenericContext<T>) instance;
    }

    public void add(Integer id, T entity) {
        entities.put(id, entity);
    }

    public T get(Integer id) {
        return entities.get(id);
    }

    public void delete(Integer id) {
        entities.remove(id);
    }

    public void update(Integer id, T entity) {
        entities.put(id, entity);
    }

    public Map<Integer, T> getAll() {
        return entities;
    }
}
