package com.us.nova.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import io.github.cdimascio.dotenv.Dotenv;
import redis.clients.jedis.Jedis;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class GenericContext<T> {

    private static final ObjectMapper mapper = new ObjectMapper()
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private final Jedis jedis;
    private final String redisPrefix = "entities:";
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public GenericContext() {
        // Use reflection to get the actual type of T
        this.type = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];

        Dotenv dotenv = Dotenv.load();
        String redisHost = dotenv.get("REDIS_HOST");
        int redisPort = Integer.parseInt(Objects.requireNonNull(dotenv.get("REDIS_PORT")));
        jedis = new Jedis(redisHost, redisPort);
    }

    public void add(Integer id, T entity) {
        try {
            jedis.set(redisPrefix + id, mapper.writeValueAsString(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T get(Integer id) {
        try {
            String data = jedis.get(redisPrefix + id);
            return data == null ? null : mapper.readValue(data, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Integer id) {
        jedis.del(redisPrefix + id);
    }

    public void update(Integer id, T entity) {
        add(id, entity);
    }

    public Map<Integer, T> getAll() {
        Map<Integer, T> allEntities = new HashMap<>();
        jedis.keys(redisPrefix + "*").forEach(key -> {
            try {
                Integer id = Integer.parseInt(key.replace(redisPrefix, ""));
                T entity = mapper.readValue(jedis.get(key), type);
                allEntities.put(id, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return allEntities;
    }
}
