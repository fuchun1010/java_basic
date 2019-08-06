package com.tank.pc;

import com.google.common.collect.Queues;

import java.util.Queue;
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
        String value = "A" + atomicInteger.incrementAndGet();
        storeCodes.add(value);
        System.out.println("produce:" + value + " 当前线程:" + Thread.currentThread().getName());
        consumer.signalAll();
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

  private Queue<String> stores = Queues.newArrayDeque();

  private Lock lock = new ReentrantLock();

  private Condition proc = lock.newCondition();

  private Condition consumer = lock.newCondition();

  private AtomicInteger atomicInteger = new AtomicInteger(0);
}
