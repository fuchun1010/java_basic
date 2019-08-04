package com.tank.basic.util;

import lombok.NonNull;
import lombok.Setter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * @author fuchun
 */
public class RedisPoolBuilder {

  public RedisPoolBuilder() {
    super();

  }

  public RedisPoolBuilder create() {
    return this.create(this.host);
  }

  public RedisPoolBuilder create(String host) {
    this.initConfig();
    this.jedisPool = new JedisPool(this.config, host, this.port);
    return this;
  }

  public RedisPoolBuilder create(String host, int db) {
    this.initConfig();
    this.jedisPool = new JedisPool(this.config, host, this.port);
    this.db = db;
    return this;
  }

  public RedisPoolBuilder create(String host, int port, int db) {
    this.initConfig();
    this.port = port;
    this.jedisPool = new JedisPool(this.config, host, this.port);
    this.db = db;
    return this;
  }

  public <T> T handleKey(@NonNull final String key, @NonNull BiFunction<String, Jedis, T> redisHandler) {
    try (Jedis redis = this.jedisPool.getResource()) {
      redis.select(db);
      return redisHandler.apply(key, redis);
    }
  }

  private void initConfig() {
    config.setTestOnReturn(true);
    config.setTestOnBorrow(true);
    config.setMaxWaitMillis(TimeUnit.MILLISECONDS.toMillis(200L));
    config.setMaxTotal(Runtime.getRuntime().availableProcessors() * 10);
  }

  private JedisPool jedisPool;

  private String host = "localhost";

  private int port = 6379;

  @Setter
  private int db = 0;

  private JedisPoolConfig config = new JedisPoolConfig();
}
