package com.tank.basic.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.TimeUnit;

/**
 * @author tank198435163.com
 */
public class ExpireKeyListenerTest {

  @Test
  public void testExpireKeyListener() {
    Jedis jedis = this.jedisPool.getResource();
    //__keyevent@0__:expired
    jedis.psubscribe(new KeyExpiredListener(), "__key*__:*");

  }

  public static class KeyExpiredListener extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
      System.out.println("xx");
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
      System.out.println("x1");
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
      System.out.println("x2");
    }
  }


  private JedisPool createRedisConnPool() {
    JedisPoolConfig config = new JedisPoolConfig();
    config.setTestOnBorrow(true);
    config.setMaxTotal(Runtime.getRuntime().availableProcessors() << 4);
    config.setMaxWaitMillis(TimeUnit.MILLISECONDS.toMillis(200L));
    config.setTestOnReturn(false);
    JedisPool jedisPool = new JedisPool(config, this.ip, this.port);
    return jedisPool;
  }

  @Before
  public void init() {
    this.jedisPool = this.createRedisConnPool();
  }

  private String ip = "localhost";

  private int port = 6379;

  private JedisPool jedisPool;
}
