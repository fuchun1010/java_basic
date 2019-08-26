package com.tank.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class ThreadPoolProcessor {


  public ExecutorService initThreadPool() {
    ThreadFactoryBuilder factoryBuilder = new ThreadFactoryBuilder();
    factoryBuilder.setNameFormat("cron-thread-%d");
    ThreadFactory threadFactory = factoryBuilder.build();

    ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 50, 50,
        TimeUnit.MICROSECONDS,
        new ArrayBlockingQueue<>(50), threadFactory);
    return executor;
  }

}
