package com.tank.pc;

import com.google.common.collect.Queues;
import com.tank.redis.RedisUtil;
import redis.clients.jedis.Tuple;

import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @author tank198435163.com
 */
public class Repository {

  public void produceData() {
    this.done(this.stores, storeCodes -> {
      if (storeCodes.isEmpty()) {
//        String value = "A" + atomicInteger.incrementAndGet();
//        storeCodes.add(value);
//        System.out.println("produce:" + value + " 当前线程:" + Thread.currentThread().getName());
        Optional<Tuple> minTuple = this.fetchMinElements();

        if (minTuple.isPresent()) {
          Tuple tuple = minTuple.get();
          try {
            int differ = new Double(tuple.getScore()).intValue();
            TimeUnit.SECONDS.sleep(differ);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          long rs = this.remove(tuple.getScore(), tuple.getScore());
          if (rs > 0) {
            storeCodes.add(tuple.getElement());
            System.out.println("produce:" + tuple.getElement() + " 当前线程:" + Thread.currentThread().getName());
            consumer.signalAll();
          }
        }
//        //TODO get redis min score elements
//        consumer.signalAll();

      }
      try {
        proc.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }


  public void consumer() {
    this.done(this.stores, storeCodes -> {
      if (!storeCodes.isEmpty()) {
        String code = storeCodes.poll();
        System.out.println("consume:" + code + " 当前线程:" + Thread.currentThread().getName());
        proc.signalAll();
      }
      try {
        this.consumer.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

  }

  private void done(Queue<String> queue, Consumer<Queue<String>> consumer) {
    try {
      lock.lock();
      consumer.accept(queue);
    } finally {
      lock.unlock();
    }
  }

  public Optional<Tuple> fetchMinElements() {
    return this.redisUtil.handleValue(this.key, (targetKey, redis) -> {
      Set<Tuple> tuples = redis.zrangeByScoreWithScores(targetKey, Double.MIN_VALUE, Double.MAX_VALUE);
      boolean isEmpty = Objects.isNull(tuples) || tuples.size() == 0;
      if (isEmpty) {
        return null;
      }
      Tuple minTuple = tuples.iterator().next();
      return minTuple;
    });

  }

  public long remove(double min, double max) {
    return this.redisUtil.handleValue(this.key, (targetKey, redis) -> redis.zremrangeByScore(targetKey, min, max)).orElse(-1L);
  }

  private Queue<String> stores = Queues.newArrayDeque();

  private Lock lock = new ReentrantLock();

  private Condition proc = lock.newCondition();

  private Condition consumer = lock.newCondition();

  private AtomicInteger atomicInteger = new AtomicInteger(0);

  private RedisUtil redisUtil = RedisUtil.build();

  private String key = "stores";
}
