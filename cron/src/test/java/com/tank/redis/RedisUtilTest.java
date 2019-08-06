package com.tank.redis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.Optional;

/**
 * @author fuchun
 */
public class RedisUtilTest {

  @Test
  public void testSet() {
    Optional<String> opt = this.redisUtil.handleValue("user:jack", (targetKey, redis) -> {
      String rs = null;
      Pipeline pipeline = redis.pipelined();
      pipeline.multi();
      Response<String> rsOpt = pipeline.set(targetKey, "0001");
      pipeline.expire(targetKey, 50);
      pipeline.exec();
      pipeline.close();
      rs = rsOpt.get();
      return rs;
    });
    Assert.assertTrue(opt.isPresent());
    System.out.println(opt.get());
  }


  @Before
  public void initRedisUtil() {
    this.redisUtil = RedisUtil.build();
  }

  private RedisUtil redisUtil;
}