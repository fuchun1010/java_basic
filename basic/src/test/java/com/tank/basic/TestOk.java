package com.tank.basic;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestOk {

  @Test
  public void testK() {
    List<Record> records = Lists.newLinkedList();

    Record a = new Record("a");
    Record b = new Record("a");

    records.add(a);

    System.out.println(records.contains(b));
  }

  @Test
  public void testLength() {
    String jsonStr = "{\n" +
        "  \"params\": {\n" +
        "    \"orgCode\": \"gzpszx\",\n" +
        "    \"itemList\": [\n" +
        "      {\n" +
        "        \"fruitCode\": \"100041\",\n" +
        "        \"total\": 1000,\n" +
        "        \"distributionType\": 1,\n" +
        "        \"fullSign\": 1,\n" +
        "        \"rule\": \"\" \n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  \"orderParams\": {\n" +
        "    \"distributionCode\": \"gzpszx\",\n" +
        "    \"stores\": [\n" +
        "      {\n" +
        "        \"storeCode\": \"1000\",\n" +
        "        \"fruits\": [\n" +
        "          {\n" +
        "            \"fruitCode\": \"100041\",\n" +
        "            \"feedBackValue\": 60,\n" +
        "            \"externalValue\": 20,\n" +
        "            \"orderedTime\": \"2019-05-16 16:58:00\"\n" +
        "          }\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  }\n" +
        "}";
    System.out.println("length = " + jsonStr.getBytes().length);
  }

  @Test
  public void testQueue() {
    List<String> lists = Arrays.asList("hello", "welcome", "to", "beijing");
    Queue<String> queue = Queues.newPriorityQueue();
    for (String s : lists) {
      queue.offer(s);
    }
    lists.clear();
    while (true) {
      String str = queue.poll();
      if (Objects.isNull(str)) {
        break;
      }
      System.out.println("len = " + queue.size());
      System.out.println(str);
    }
  }


  @Test
  public void cleanDuplicateCode() {
    //110463,110461,110462
    //110462,110461,110466,110463
    //110461,110462,110463,110466
    List<String> g1 = Arrays.asList("110463", "110461", "110462");
    List<String> g2 = Arrays.asList("110462", "110461", "110466", "110463");
    List<String> g3 = Arrays.asList("110461", "110462", "110463", "110466");


    // Map<String, List<String>> groups = Maps.newHashMap();


    Map<String, Integer> counter = Maps.newHashMap();

    Set<String> allCodes = Sets.newHashSet();

    allCodes.addAll(g1);
    allCodes.addAll(g2);
    allCodes.addAll(g3);

    allCodes.stream().forEach(c -> counter.putIfAbsent(c, 0));

    g1 = filterDuplicate(g1, counter);
    g2 = filterDuplicate(g2, counter);
    g3 = filterDuplicate(g3, counter);
    List<List<String>> groups = Stream.of(g1, g2, g3).filter(list -> !list.isEmpty()).collect(Collectors.toList());


    System.out.println("");
  }

  private List<String> filterDuplicate(List<String> g1, Map<String, Integer> counter) {
    List<String> rs = g1;
    for (String s : rs) {
      boolean isOk = counter.get(s) == 0;
      if (isOk) {
        counter.put(s, 1);
      } else {
        rs = rs.stream().filter(c -> !c.equalsIgnoreCase(s)).collect(Collectors.toList());
      }
    }
    return rs;
  }


  @Test
  public void reduceElement() {
    Set<String> elements = Sets.newHashSet();
    elements.add("10001");
    elements.add("10002");

  }


  @Data
  @AllArgsConstructor
  static class Record {

    private String name;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Record record = (Record) o;
      return Objects.equals(name, record.name);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name);
    }
  }
}
