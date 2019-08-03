package com.tank.basic;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class TestStore {

  @Test
  public void testAdd() {
    int start = 1;
    int end = 1000000;
    LocalDateTime startTime = LocalDateTime.now();
    int rs = IntStream.range(start, end)
        .parallel()
        .filter(i -> Math.floorMod(i, 2) == 0)
        .filter(i -> Math.floorMod(i, 50) == 0)
        .filter(i -> Math.floorMod(i, 7) == 0)
        .filter(i -> {
          try {
            TimeUnit.MILLISECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return i > 100;
        }).reduce(0, Integer::sum);
    LocalDateTime endTime = LocalDateTime.now();
    long cost = Duration.between(startTime, endTime).toMillis();
    System.out.println("p rs = " + rs + " cost = " + cost);
  }

  @Test
  public void testAdd2() {
    int start = 1;
    int end = 1000000;
    LocalDateTime startTime = LocalDateTime.now();
    int rs = IntStream.range(start, end)
        .filter(i -> Math.floorMod(i, 2) == 0)
        .filter(i -> Math.floorMod(i, 50) == 0)
        .filter(i -> Math.floorMod(i, 7) == 0)
        .filter(i -> {
          try {
            TimeUnit.MILLISECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return i > 100;
        }).reduce(0, Integer::sum);
    LocalDateTime endTime = LocalDateTime.now();
    long cost = Duration.between(startTime, endTime).toMillis();
    System.out.println("c rs = " + rs + " cost = " + cost);
  }
}
