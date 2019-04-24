package com.tank.basic.other;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestTime {

  @Test
  public void testMillions() {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    try {
      String ip = InetAddress.getLocalHost().getHostAddress();
      System.out.println(ip.replace(".", ""));
      System.out.println(Math.abs(Objects.hash(ip)));
      System.out.println(Math.abs(Objects.hash(ip)) >> 16);
      System.out.println(Integer.MAX_VALUE);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

  }

  @Test
  public void testOrderId() {
    if (id.get() == MAX_VALUE) {
      this.id = new AtomicInteger(MAX_VALUE);
    }
    int incr = this.id.incrementAndGet();
    String day = this.dateFormat.format(LocalDate.now());
    StringJoiner joiner = new StringJoiner("");
    joiner.add("A");
    joiner.add(day);
    joiner.add(String.valueOf(incr));
    System.out.println("orderId==>" + joiner.toString() + " bytes:" + joiner.toString().getBytes().length);
  }


  @Before
  public void init() {
    this.id = new AtomicInteger(MAX_VALUE);
    this.dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
  }


  @Test
  public void testThreadInitialize() {

    ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("booking-order-response-%d").build();
    int corePoolSize = 1;
    int maxPoolSize = 2;
    long keepAlive = 0L;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
        corePoolSize,
        maxPoolSize,
        keepAlive,
        TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(50),
        threadFactory,
        (r, pool) -> new Thread(r).start()
    );
  }


  @Test
  public void testKeyLength() {
    final String key = "distribute:s0001";
    System.out.println("size=" + key.getBytes().length);
  }

  @Test
  public void testDiffer() {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    final String currentDay = "2019-04-07 23:59:59";
    LocalDateTime end = LocalDateTime.from(df.parse(currentDay));
    LocalDateTime start = LocalDateTime.now();
    long seconds = Duration.between(start, end).toMinutes() * 60;
    System.out.println("differ second--->" + seconds);

  }

  private AtomicInteger id = null;

  private int MAX_VALUE = 100000;
  private DateTimeFormatter dateFormat = null;
}
