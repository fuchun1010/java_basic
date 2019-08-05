package com.tank.redis;

import lombok.NonNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author tank198435163.com
 */
public class RedisUtil {

  public static RedisUtil build() {
    return Single.Instance.instance();
  }
  
  public <R> Optional<R> handleValue(@NonNull final String key, BiFunction<String, Jedis, R> fun) {
    JedisPool pool = Single.Instance.redisPoolInstance();
    try (Jedis redis = pool.getResource()) {
      R result = fun.apply(key, redis);
      return Optional.ofNullable(result);
    }
  }

  private JedisPoolConfig initRedisPoolConfig() {

    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(50);
    config.setMaxWaitMillis(50);
    config.setTestOnBorrow(true);
    config.setTestOnReturn(true);

    return config;
  }

  private enum Single {

    Instance("localhost", 6379);

    Single(String host, Integer port) {
      this.redisUtil = new RedisUtil();
      JedisPoolConfig poolConfig = this.redisUtil.initRedisPoolConfig();
      this.jedisPool = new JedisPool(poolConfig, host, port);
    }

    public JedisPool redisPoolInstance() {
      return this.jedisPool;
    }

    public RedisUtil instance() {
      return this.redisUtil;
    }

    private RedisUtil redisUtil;

    private JedisPool jedisPool;
  }

  private RedisUtil() {

  }

  private String host;

  private Integer port;

}
