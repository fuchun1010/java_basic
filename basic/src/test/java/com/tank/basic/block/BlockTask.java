package com.tank.basic.block;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author red-tank
 */
public class BlockTask {

  @Test
  public void runBlockTask() {

    CountDownLatch lock = new CountDownLatch(1);

    new Thread(() -> {
      try {
        System.out.println("enter");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("over");
        lock.countDown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    System.out.println("wait block task ok");
    try {
      lock.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("main task");

  }

  @Test
  @SneakyThrows
  public void runBlockTask2() {

    Object lock = new Object();

    new Thread(() -> {
      try {
        System.out.println("enter");
        System.out.println("thread:" + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(2);
        System.out.println("over");
        synchronized (lock) {
          System.out.println("thread:" + Thread.currentThread().getName());
          lock.notifyAll();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    System.out.println("wait block task ok");
    synchronized (lock) {
      System.out.println("main:" + Thread.currentThread().getName());
      lock.wait();
    }
    System.out.println("main task");

  }


  @Test
  public void testThreadLocal() {
    ThreadLocal<Integer> localData = ThreadLocal.withInitial(() -> 0);

    new Thread(() -> {
      int initValue = localData.get();
      System.out.println("thread name is :" + Thread.currentThread().getName() + ":" + (++initValue));
      localData.set(initValue);
    }, "A").start();


    new Thread(() -> {
      int initValue = localData.get();
      System.out.println("thread name is :" + Thread.currentThread().getName() + (++initValue));
      localData.set(initValue);
    }, "B").start();

  }

  @Test
  public void moveBit() {
    int a = 1 << 16 - 1;
    int b = a >> 8;

    int c = b & 1;
    System.out.println(Integer.toBinaryString(a));
    System.out.println(Integer.toBinaryString(b));
    System.out.println(Integer.toBinaryString(c));

  }

}
