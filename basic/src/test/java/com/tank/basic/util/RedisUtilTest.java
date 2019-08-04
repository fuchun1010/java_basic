package com.tank.basic.util;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.params.SetParams;

public class RedisUtilTest {

  @Test
  public void testSetOp() {
    SetParams params = new SetParams();
    params.nx().ex(50);
    String responseCode = redisPoolBuilder.handleKey("lock:imported", (key, redis) -> redis.set(key, "lock", params));
    System.out.println(responseCode);
  }


  @Before
  public void init() {
    this.redisPoolBuilder = RedisUtil.createRedisBuilder();
    redisPoolBuilder.setDb(3);
  }

  private RedisPoolBuilder redisPoolBuilder = null;


}