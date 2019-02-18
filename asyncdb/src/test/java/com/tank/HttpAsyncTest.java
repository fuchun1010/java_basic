package com.tank;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class HttpAsyncTest {

  @Before
  public void init() {
    this.cpuCores = Runtime.getRuntime().availableProcessors();
  }

  @Test
  public void testAsyncRequest() {
    Flux.fromIterable(mockUrls)
        .parallel(this.cpuCores)
        .runOn(Schedulers.parallel())
        .doOnNext(d -> System.out.println(d + " = " + Thread.currentThread().getName()))
        .doOnError(e -> System.out.println(e.getMessage()))
        .subscribe();
    System.out.println("testAsyncRequest ok");
  }


  @Test
  public void test2() {
    Flux.fromIterable(Arrays.<Integer>asList(1, 2, 3))
        .map(d -> Integer.sum(d, 1))
        .parallel(Runtime.getRuntime().availableProcessors())
        .runOn(Schedulers.parallel())
        .doOnNext(System.out::println)
        .subscribe();
  }

  @Test
  public void test3() {
    val data = Arrays.<Integer>asList(1, 2, 3, 4, 5, 6);
    Mono<List<Integer>> mono = Flux.fromIterable(data).map(i -> i + 1).collectList();
    List<Integer> rs = mono.block();
    rs.forEach(System.out::println);
  }

  @Test
  public void test4() {
    Flux<Mono<Integer>> flux = Flux.fromIterable(data).map(d -> Mono.fromSupplier(() -> d));
    flux.doOnEach(mono -> System.out.println("v= " + mono.get().block())).subscribe();
  }

  @Test
  public void test5() {
    Mono<List<Integer>> mono = Flux.range(1, 5).collectList();
    mono.block().forEach(System.out::println);
  }

  @Test
  public void test6() {
    Spike<Integer> spike = new Spike<>();
    spike.done(5, t -> {
      System.out.println("name->" + Thread.currentThread().getName());
    });
  }

  @Test
  public void test7() {
    Flux.fromIterable(data)
        .parallel(Runtime.getRuntime().availableProcessors())
        .runOn(Schedulers.parallel())
        .map(d -> {
          System.out.println(d + " A--->" + Thread.currentThread().getName());
          return d;
        })
        .doOnNext(d -> {
          val x = d + 1;
          System.out.println(d + " B--->" + Thread.currentThread().getName());
        })
        .subscribe();
  }

  @Test
  public void test8() {
    Integer[] data = new Integer[]{1, 2, 3};
    Flux<Integer> flux = Flux.fromArray(data).flatMap(d -> Mono.just(d));

  }


  private static class Spike<T> {

    public void done(T t, Consumer<T> consumer) {
      consumer.accept(t);
    }
  }

  private List<String> mockUrls = Arrays.asList("a1", "a2", "a3", "a4");

  List<Integer> data = Arrays.<Integer>asList(1, 2, 3, 4, 5, 6, 7);

  private int cpuCores = 0;
}
