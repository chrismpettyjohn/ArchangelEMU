package com.us.nova.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class GenericContext<T> {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private static final Map<Class<?>, GenericContext<?>> instances = new HashMap<>();

    private final Map<Integer, T> store = Collections.synchronizedMap(new HashMap<>());
    private final Class<T> type;

    protected GenericContext(Class<T> type) {
        this.type = type;
    }

    public static synchronized <T> GenericContext<T> getInstance(Class<T> type) {
        if (!instances.containsKey(type)) {
            instances.put(type, new GenericContext<T>(type) {});
        }
        return (GenericContext<T>) instances.get(type);
    }

    public void add(Integer id, T entity) {
        store.put(id, entity);
    }

    public T get(Integer id) {
        return store.get(id);
    }

    public void delete(Integer id) {
        store.remove(id);
    }

    public void update(Integer id, T entity) {
        add(id, entity);
    }

    public Map<Integer, T> getAll() {
        return new HashMap<>(store);
    }
}
