package com.tank.basic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestArray {

  @Before
  public void init() {
    rs = new LinkedList<>();
  }

  @Test
  public void testContains() {
    List<String> tmpStores = Arrays.asList("store:003", "store:002");
    List<String> stores = Arrays.<String>asList("store:001", "store:002");

    rs.addAll(tmpStores);
    rs.addAll(stores);

    Assert.assertTrue(rs.size() == 4);

    Set<String> lastStores = rs.parallelStream().collect(Collectors.toSet());
    Assert.assertTrue(lastStores.size() == 3);

  }

  @Test
  public void testMod1() {
    int a = 21;
    System.out.println(a & (mod - 1));
  }

  @Test
  public void testDiv1() {
    int a = 21;
    System.out.println(a >> Integer.bitCount(this.mod - 1));
  }

  @Test
  public void test2() {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime rs = LocalDateTime.parse("2018-10-24 19:43:45", df);
    ZonedDateTime zone = rs.atZone(ZoneId.systemDefault());
    System.out.println(zone.toInstant().getEpochSecond());
  }

  @Test
  public void testSecond() {
    int rs = Integer.MAX_VALUE / 3600;
    System.out.println(rs / (24 * 365));
  }

  private int mod = 8;

  private List<String> rs;
}
