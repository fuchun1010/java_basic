package com.tank.basic;

import lombok.NonNull;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
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

  @Test
  public void testAccumulate() {
    int a = 1;
    int rs = a << 1;
    rs = rs ^ 1;
    System.out.println(rs);
  }

  @Test
  public void testProcessWithCondition() {
    Optional<String> rs = this.processWithCondition("s0001",
        System.out::println,
        storeCode -> Objects.nonNull(storeCode) ? Optional.empty() : Optional.of("not allowed null"),
        storeCode -> storeCode.length() > 0 ? Optional.empty() : Optional.of("not allowed empty"));
    Assert.assertTrue(!rs.isPresent());
  }


  private <T> Optional<String> processWithCondition(@NonNull final T storeCode,
                                                    @NonNull final Consumer<T> handler,
                                                    @NonNull Function<T, Optional<String>>... judgements) {

    for (Function<T, Optional<String>> judgement : judgements) {
      Optional<String> tips = judgement.apply(storeCode);
      if (tips.isPresent()) {
        return tips;
      }
    }
    handler.accept(storeCode);
    return Optional.empty();
  }
}
