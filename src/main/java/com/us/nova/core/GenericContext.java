package com.us.nova.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class GenericContext<T> {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private static final JedisPoolConfig poolConfig = new JedisPoolConfig();
    private static JedisPool jedisPool;
    private final String redisPrefix;
    private final Class<T> type;

    public GenericContext(Class<T> type) {
        this.type = type;
        this.redisPrefix = type.getSimpleName() + ":"; // Use the simple class name as the prefix

        Dotenv dotenv = Dotenv.load();
        String redisHost = dotenv.get("REDIS_HOST");
        int redisPort = Integer.parseInt(Objects.requireNonNull(dotenv.get("REDIS_PORT")));
        poolConfig.setMaxTotal(50); // Set maximum connections
        poolConfig.setMaxIdle(10); // Set max idle connections
        poolConfig.setMinIdle(2); // Set min idle connections
        jedisPool = new JedisPool(poolConfig, redisHost, redisPort, 2000);
    }

    public void add(Integer id, T entity) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(redisPrefix + id, mapper.writeValueAsString(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T get(Integer id) {
        try (Jedis jedis = jedisPool.getResource()) {
            String data = jedis.get(redisPrefix + id);
            return data == null ? null : mapper.readValue(data, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Integer id) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(redisPrefix + id);
        }
    }

    public void update(Integer id, T entity) {
        add(id, entity);
    }

    public Map<Integer, T> getAll() {
        Map<Integer, T> allEntities = new HashMap<>();
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.keys(redisPrefix + "*").forEach(key -> {
                try {
                    Integer id = Integer.parseInt(key.replace(redisPrefix, ""));
                    T entity = mapper.readValue(jedis.get(key), type);
                    allEntities.put(id, entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return allEntities;
    }
}
