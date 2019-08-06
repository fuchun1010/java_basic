package com.tank.spec;

import com.tank.redis.RedisUtil;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Tuple;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class ThreadSpec {

  @Test
  @SneakyThrows
  public void testThread() {
    int seed = 10;
    CountDownLatch latch = new CountDownLatch(seed);
    new Thread(() -> {
      while (true) {
        try {
          int value = ThreadLocalRandom.current().nextInt(30);
          TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " handle something ");
        latch.countDown();
      }
    }, "Thread-A").start();
    latch.await();
  }

  @Test
  public void testPipe() {
    Optional<String> strOpt = RedisUtil.build().handleValue("ai", (target, redis) -> {
      Response<String> response = this.executePipe(target, redis, (tmpKey, pipe) -> pipe.set(tmpKey, "hello"));
      return response.get();
    });
    Assert.assertTrue(strOpt.isPresent());
    System.out.println(strOpt.get());
  }

  @Test
  public void testFetchMin() {
    String key = "submit:gzpszx:stores";
    Optional<Double> minValueOpt = this.redisUtil.handleValue(key, (target, redis) -> {
      Response<Set<Tuple>> response = this.executePipe(target, redis, (tmpKey, pipe) -> pipe.zrangeWithScores(tmpKey, 0, -1));
      boolean isEmpty = Objects.isNull(response) || response.get().size() == 0;
      if (isEmpty) {
        return -1d;
      }
      Tuple store = response.get().iterator().next();
      return store.getScore();
    });
    System.out.println(minValueOpt.get());
  }


  @Test
  public void testRandomValue() {
    IntStream.range(0, 10).forEach(i -> {
      int value = ThreadLocalRandom.current().nextInt(30);
      System.out.println("value ==> " + value);
    });
  }

  @Before
  public void init() {
    this.redisUtil = RedisUtil.build();
  }

  private RedisUtil redisUtil;

  private static class ScheduleRunner implements Runnable {

    public ScheduleRunner(@NonNull final String key) {
      redisUtil = RedisUtil.build();
      this.key = key;
    }

    @Override
    public void run() {
      while (!isOver) {
        this.redisUtil.handleValue(this.key, (targetKey, redis) -> {
          return null;
        });
      }
    }


    private volatile boolean isOver = false;

    private RedisUtil redisUtil;

    private String key;
  }

  private <R> R executePipe(String key, Jedis redis, BiFunction<String, Pipeline, R> fun) {
    Pipeline pipeline = redis.pipelined();
    pipeline.multi();
    R rs = fun.apply(key, pipeline);
    pipeline.expire(key, 600);
    pipeline.exec();
    pipeline.close();
    return rs;
  }

}
