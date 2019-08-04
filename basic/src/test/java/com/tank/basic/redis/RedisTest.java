package com.tank.basic.redis;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RedisTest {

  @Test
  public void testHashValue() {
    val disCodes = Arrays.asList("szpszx", "gzpszx", "njpszx");
    disCodes.stream().map(Objects::hashCode).map(Math::abs).forEach(System.out::println);
  }

  @Test
  public void testSetBytesArr() {

    String rs = this.handleValue(redisConn -> {
      byte[] login = new byte[365];
      login[index] = 1;
      return redisConn.set(key, login);
    });

    System.out.println(rs);
  }

  @Test
  public void testFetchBytes() {
    byte[] valueArr = this.handleValue(redisConn -> redisConn.get(this.key));
    if (valueArr != null && valueArr.length > 0) {
      System.out.println(valueArr[index]);
    }
  }


  @Test
  public void testListLen() {
    Long count = this.handleValue(redis -> redis.llen("fruits"));
    Assert.assertTrue(count != null);
    Assert.assertTrue(count.intValue() == 4);
  }

  @Test
  public void testIterateList() {
    List<String> items = this.handleValue(redis -> redis.lrange("fruits", 0, -1));
    Assert.assertTrue(items != null);
    Assert.assertTrue(items.size() > 0);
    for (String item : items) {
      System.out.println("item = " + item);
    }
  }

  @Test
  public void testIndexItemOfList() {
    String item = this.handleValue(redis -> redis.lindex("fruits", 1));
    Assert.assertTrue(item != null);
    System.out.println(item);
  }

  @Test
  @SneakyThrows
  public void testBlockPop() {
    CountDownLatch countDownLatch = new CountDownLatch(100);
    ThreadPoolExecutor threadPoolExecutor = this.customerTreadPool();
    for (int i = 0; i < 2; i++) {
      threadPoolExecutor.execute(() -> {
        while (true) {
          List<String> items = this.handleValue(redis -> redis.blpop(0, "fruits"));
          items.remove(0);
          System.out.println(Thread.currentThread().getName() + "->" + items + " latest count:" + countDownLatch.getCount());
          countDownLatch.countDown();
        }
      });
    }
    countDownLatch.await();
  }

  @Test
  public void testReadAllSortedSet() {
    Collection<String> items = this.handleValue(redis -> redis.zrange("ireader", 0, -1));
    Assert.assertTrue(items != null);
    Assert.assertTrue(items.size() > 0);
    Iterator<String> iterator = items.iterator();
    while (iterator.hasNext()) {
      String item = iterator.next();
      System.out.println(item);
    }
    System.out.println("================");
  }

  @Test
  public void testReadAllSortedByRangeScore() {
    Collection<String> items = this.handleValue(redis -> redis.zrangeByScore("ireader", Double.MIN_VALUE, Double.MAX_VALUE));
    Assert.assertTrue(items != null);
    Assert.assertTrue(items.size() > 0);
    Iterator<String> iterator = items.iterator();
    while (iterator.hasNext()) {
      String item = iterator.next();
      System.out.println(item);
    }
    System.out.println("================");
  }

  @Test
  public void testRankByScore2() {
    Collection<String> items = this.handleValue("ireader", (key, redis) -> redis.zrevrange(key, 0, -1));
    Assert.assertTrue(items != null);
    Assert.assertTrue(items.size() > 0);
    items.stream().forEach(System.out::println);
  }

  @Test
  public void testBinaryArrByte() {
    String key = "u:18623377391";
    String status = this.handleValue(key, (targetKey, redis) -> {
      byte[] arr = new byte[366];
      arr[11] = 1;
      arr[36] = 1;
      return redis.set(targetKey.getBytes(), arr);
    });
    Assert.assertNotNull(status);
    Assert.assertTrue("ok".equalsIgnoreCase(status));
  }

  @Test
  public void testBinaryValue() {
    String key = "u:18623377391";
    Long count = this.handleValue(key, (targetKey, redis) -> redis.bitcount(targetKey));
    Assert.assertNotNull(count);
    Assert.assertTrue(count.longValue() > 0);
    System.out.println(count);
  }

  @Test
  public void testIsAllowedAccess() {
    for (int i = 0; i < 10; i++) {
      System.out.println(this.isAllowedAccess("jack", "select", 60, 5));
    }

  }

  private boolean isAllowedAccess(String name, String action, int period, int restriction) {
    String key = String.format("hit:%s:%s", name, action);

    boolean isAllowed = this.handleValue(key, (targetKey, redis) -> {
      Pipeline pipeline = redis.pipelined();
      pipeline.multi();
      long now = System.currentTimeMillis();
      pipeline.zadd(targetKey, now, String.valueOf(now));
      pipeline.zremrangeByScore(targetKey, 0, now - period * 1000);
      Response<Long> count = pipeline.zcard(targetKey);
      pipeline.expire(targetKey, period + 1);
      pipeline.exec();
      pipeline.close();
      System.out.println(count.get());
      return count.get() <= restriction;
    });

    return isAllowed;
  }

  public <T> T handleValue(Function<Jedis, T> fun) {
    try (Jedis jedis = jedisPool.getResource()) {
      jedis.select(db);
      return fun.apply(jedis);
    }
  }

  public <T> T handleValue(String key, BiFunction<String, Jedis, T> fun) {
    try (Jedis jedis = jedisPool.getResource()) {
      jedis.select(db);
      jedis.expire(key, 3600);
      return fun.apply(key, jedis);
    }
  }


  private ThreadPoolExecutor customerTreadPool() {
    ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
    builder.setNameFormat("redis-queue-%s");
    int coreSize = 1;
    int maxPool = Runtime.getRuntime().availableProcessors();
    int keepAlive = 50;
    ThreadPoolExecutor executor = new ThreadPoolExecutor(coreSize, maxPool, keepAlive,
        TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(50));
    return executor;
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


  private JedisPool jedisPool;

  private String ip = "localhost";

  private int port = 6379;

  private int db = 3;

  int index = 10;

  private byte[] key = "u:18623377391".getBytes();

}
