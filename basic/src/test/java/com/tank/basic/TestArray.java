package com.tank.basic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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


  private List<String> rs;
}
