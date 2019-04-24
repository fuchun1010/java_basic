package com.tank.basic;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class TestStore {


  @Test
  public void hashStores() {
    File file = new File("/Users/tank198435163.com/javadone/java_basic/basic/src/test/resources/store.csv");
    Set<String> lines = Sets.newHashSet();
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

      while (true) {
        String line = in.readLine();
        if (Objects.isNull(line)) {
          break;
        }
        lines.add(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (Objects.nonNull(in)) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    int seed = 190;
    Map<Integer, Integer> rs = lines.stream().map(line -> {
      int hashCode = Math.abs(Objects.hashCode(line));
      int v = Math.floorMod(hashCode, seed);
      Map<Integer, Integer> d = Maps.newHashMap();
      d.put(v, 1);
      return d;
    }).reduce(Maps.<Integer, Integer>newHashMap(), (a, b) -> {

      int key = b.keySet().iterator().next();

      if (!a.containsKey(key)) {
        a.put(key, 1);
      } else {
        int v = a.get(key) + 1;
        a.remove(key);
        a.put(key, v);
      }
      return a;
    });


    rs.values().stream().min(Integer::compareTo).ifPresent(System.out::println);
    rs.values().stream().max(Integer::compareTo).ifPresent(System.out::println);

    System.out.println(rs);
  }

}
