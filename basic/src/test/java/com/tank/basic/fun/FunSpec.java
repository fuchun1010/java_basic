package com.tank.basic.fun;

import com.annimon.stream.Collectors;
import com.annimon.stream.IntStream;
import com.annimon.stream.Stream;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.annimon.stream.function.Function.Util.safe;


public class FunSpec {

  @Test
  public void testPrint() {
    this.toStream(1, 2, 3, 4, 5, 6).forEach(System.out::println);
  }

  @Test
  public void testMax() {
    int rs = this.toStream(1, 2, 3, 4, 100).reduce(0, (a, b) -> {
      b = Math.max(a, b);
      return b;
    }).intValue();
    System.out.println(rs);
  }

  @Test
  public void testNotNullElements() {
    this.toStream("1", null, "4", "5", null)
        .withoutNulls()
        .filterNot("1"::equalsIgnoreCase)
        .forEach(System.out::println);
  }

  @Test
  public void testSorted() {
    List<Integer> data = initList();
    this.toStream(data).sortBy(a -> a)
        .collect(Collectors.toList())
        .forEach(System.out::println);

  }

  @Test
  public void testTry() {
    List<Integer> data = initList();
    this.toStream(data)
        .map(safe(this::addOnce))
        .forEach(System.out::println);
  }


  @Test
  public void testWindow() {
    int start = 0;
    int end = 10;
    int end1 = 100;
    Stream.range(start, end).forEach(System.out::println);
    Stream.range(start, end).forEachIndexed((index, a) -> {
      final String data = String.format("index:%d, data:%d", index, a);
      System.out.println(data);
    });

    Stream.rangeClosed(start, end1).slidingWindow(4, 9).forEach(list -> {
      StringJoiner joiner = this.toStream(list).reduce(new StringJoiner(","), (a, b) -> {
        a = a.add(String.valueOf(b));
        return a;
      });
      System.out.println(joiner);
    });
    System.out.println("-------------------");
    Stream.rangeClosed(start, end1).sample(4).forEach(System.out::println);
  }

  @Test
  public void testCompare() {
    int rs = IntStream.of(1, 1, 1, 2, 3).distinct().custom((stream) -> stream.sum());
    System.out.println(rs);
  }


  @Test
  public void testGroup() {
    Map<String, List<Job>> rs = this.toStream(
        new Job().setName("driver").setSalary(6500)
        , new Job().setName("driver").setSalary(3575.2)
        , new Job().setName("teacher").setSalary(4300)
        , new Job().setName("student").setSalary(0)
    ).groupBy(Job::getName)
        .reduce(Maps.newHashMap(), (a, b) -> {
          String key = b.getKey();
          List<Job> jobs = b.getValue();
          if (a.containsKey(key)) {
            a.get(key).addAll(jobs);
          } else {
            a.putIfAbsent(key, jobs);
          }
          return a;
        });


    for (Map.Entry<String, List<Job>> entry : rs.entrySet()) {
      String key = entry.getKey();
      List<Job> jobs = entry.getValue();
      double salary = this.toStream(jobs).map(Job::getSalary)
          .map(BigDecimal::new)
          .reduce(new BigDecimal(0.0), BigDecimal::add)
          .doubleValue();
      String dis = String.format("job name = %s,salary = %f", key, salary);
      System.out.println(dis);
    }
  }

  @Data
  @Accessors(chain = true)
  @AllArgsConstructor
  @NoArgsConstructor
  private static class Job {

    String name;

    double salary;

    @Override
    public String toString() {

      return String.format("name:{%s},salary:{%f}", this.name, this.salary);
    }
  }

  private List<Integer> initList() {
    List<Integer> data = Lists.newLinkedList();
    data.add(1);
    data.add(5);
    data.add(2);
    return data;
  }


  private Integer addOnce(int a) throws Exception {
    return Integer.sum(a, 1);
  }

  private <T> Stream<T> toStream(T... elements) {
    return Stream.of(elements);
  }

  private <T> Stream<T> toStream(@NonNull final Collection<T> elements) {
    return Stream.of(elements);
  }
}
