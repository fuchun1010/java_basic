package com.tank.basic.wheel;

import com.google.common.collect.Sets;
import com.sun.istack.internal.NotNull;

import java.lang.reflect.Array;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tank198435163.com
 */
public class RingBufferWheel<T extends Mission> {


  public RingBufferWheel(final ExecutorService executorService, Class<T> clazz) {
    this.ringBuffer = ((Set<T>[]) Array.newInstance(clazz, this.RING_BUFFER_SLOT));
    this.executorService = executorService;
  }

  public RingBufferWheel(final ExecutorService executorService, int bufferSize, Class<T> clazz) {
    this.RING_BUFFER_SLOT = bufferSize;
    this.ringBuffer = ((Set<T>[]) Array.newInstance(clazz, this.RING_BUFFER_SLOT));
    this.executorService = executorService;
  }

  public RingBufferWheel(Class<T> clazz) {
    this.ringBuffer = ((Set<T>[]) Array.newInstance(clazz, this.RING_BUFFER_SLOT));
    this.executorService = Executors.newFixedThreadPool(2);
  }

  public void add(@NotNull final T task) {
    int delay = task.getDelay();
    int key = this.mod(delay);
    Set<T> target = this.ringBuffer[key];
    if (target == null) {
      this.ringBuffer[key] = Sets.newHashSet();
    }
    int cycle = this.cycle(delay);
    task.setCycle(cycle);
    try {
      lock.lock();
      this.ringBuffer[key].add(task);
    } finally {
      lock.unlock();
    }
  }

  public void start() {
    new Thread(() -> {
      int index = 0;
      while (this.isRunning) {
        Set<T> tasks = this.ringBuffer[index];
        if (tasks == null) {
          index++;
        }
        for (T task : tasks) {
          
        }
      }
    }).start();
  }

  public void stop() {
    this.isRunning = false;
  }


  private int mod(@NotNull final Integer delay) {
    return delay & (this.RING_BUFFER_SLOT - 1);
  }

  private int cycle(@NotNull final Integer delay) {
    return delay >> Integer.bitCount((this.RING_BUFFER_SLOT - 1));
  }

  private Set<T>[] ringBuffer = null;

  private AtomicInteger taskSize = new AtomicInteger();

  private ExecutorService executorService;

  private int RING_BUFFER_SLOT = 8;

  private volatile boolean isRunning = true;

  private ReentrantLock lock = new ReentrantLock();
}
