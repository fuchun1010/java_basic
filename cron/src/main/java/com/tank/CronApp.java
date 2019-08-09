package com.tank;

import com.tank.pc.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author fuchun
 */
public class CronApp {
  public static void main(String[] args) {
    Repository repository = new Repository();

    new Thread(() -> {
      while (true) {
        repository.produceData();
      }
    }, "ProducerThread").start();
    new Thread(() -> {
      while (true) {
        try {
          TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        repository.consumer();
      }
    }, "ConsumerThreadA").start();
    new Thread(() -> {
      while (true) {
        try {
          TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        repository.consumer();
      }
    }, "ConsumerThreadB").start();

  }
}
