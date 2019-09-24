package com.tank.basic.id;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import lombok.val;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IdTest {


  @Test
  public void testDayOfYear() {
    int day = LocalDateTime.now().getDayOfYear();
    System.out.println(day);
  }

  @Test
  public void testConvertMillionsToDateTime() {
    String dateTimeStr = this.convertToStr(System.currentTimeMillis(), millions -> LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneOffset.systemDefault()));
    System.out.println(dateTimeStr);
  }


  public <T> String convertToStr(final T data, Function<T, LocalDateTime> fun) {
    LocalDateTime dateTime = fun.apply(data);
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String dateTimeStr = df.format(dateTime);
    return dateTimeStr;
  }


  @Test
  public void testIdGenerate() {
    String id = ObjectId.get().toString();
    Assert.assertTrue(id.length() == 24);
    System.out.println(id);
  }

  @Test
  public void testBigDecimal() {
    BigDecimal rs = this.calc(15.7, 6.6, (a, b) -> a.add(b));
    int isEq = this.calc(rs.doubleValue(), 22.3, (a, b) -> a.compareTo(b));
    long x = 1169070655262425123L;
    Assert.assertTrue(isEq == 0);
  }

  @Test
  public void test2() {
    val v = -1 << 2;
    System.out.println(v);
    val v1 = -1 ^ v;
    val v2 = 1L << 32;
    System.out.println(v1);
    System.out.println(v2);
  }

  @Test
  public void testNetWork() {
    Enumeration<NetworkInterface> networks = null;
    try {
      networks = NetworkInterface.getNetworkInterfaces();
    } catch (SocketException e) {
      e.printStackTrace();
    }
    for (; ; ) {
      boolean isOk = Objects.nonNull(networks) && networks.hasMoreElements();
      if (!isOk) {
        break;
      }
      NetworkInterface network = networks.nextElement();
      Enumeration<InetAddress> address = network.getInetAddresses();
      while (address.hasMoreElements()) {
        InetAddress tmpAddress = address.nextElement();
        if (tmpAddress.isLoopbackAddress()) {
          continue;
        }

        if (tmpAddress instanceof Inet6Address) {
          continue;
        }
        System.out.println(tmpAddress.getHostAddress());
      }
    }
  }

  @Test
  public void testMovePosition() {
    long workerIdBits = 10L;
    long v = -1 << workerIdBits;
    long d = -1 ^ v;
    System.out.println(v);
    System.out.println(d);
  }

  @Test
  public void testAdd() {
    List<Integer> rs = IntStream.rangeClosed(0, 99).map(i -> i + 1).boxed().collect(Collectors.toList());
    Queue<Integer> queue = Queues.newPriorityQueue();
    Integer value = rs.stream().reduce(0, Integer::sum);
    for (Integer data : rs) {
      queue.add(data);
    }
    rs.clear();
    Integer tmpValue = this.add(queue);

    Assert.assertTrue(tmpValue == value);
  }

  @Test
  public void testDir() {
    String dir = System.getProperty("java.io.tmpdir");
    System.out.println(dir);
  }

  @Test
  public void testRandom() {
    List fruits = Lists.newLinkedList();
    fruits.add("a");
    fruits.add("b");
    fruits.add("c");
    fruits.add("d");
    ThreadLocalRandom random = ThreadLocalRandom.current();
    int num = fruits.size();
    int seed = 2;
    for (int i = 0; i < 100; i++) {
      System.out.println(random.nextInt(seed));
    }
  }

  @Test
  public void testScale() {
    int weight = 108;
    DecimalFormat df = new DecimalFormat("0.00");
    System.out.println(df.format((double) weight / 100));
  }

  private Integer add(Queue<Integer> queue) {
    List<Integer> data = Lists.newLinkedList();
    if (queue.size() <= 20) {
      while (!queue.isEmpty()) {
        Integer a = queue.poll();
        data.add(a);
      }
      return data.stream().reduce(0, Integer::sum);
    } else {
      Queue<Integer> newQueue = Queues.newPriorityQueue();
      while (newQueue.size() < 20) {
        Integer a = queue.poll();
        newQueue.add(a);
      }
      return add(newQueue) + add(queue);
    }
  }


  private <T> T calc(final double a, final double b, BiFunction<BigDecimal, BigDecimal, T> fun) {
    BigDecimal f = new BigDecimal(a);
    BigDecimal s = new BigDecimal(b);
    return fun.apply(f, s);
  }

}
