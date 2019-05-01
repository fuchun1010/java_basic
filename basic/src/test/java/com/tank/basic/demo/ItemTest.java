package com.tank.basic.demo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.val;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class ItemTest {


  @Test
  public void tm() {
    val xx = new TreeMap<String, String>();
    xx.putIfAbsent("a", "b");
    xx.putIfAbsent("c", "d");
    Iterator<String> iter = xx.keySet().iterator();

    while (iter.hasNext()) {
      String value = iter.next();
      System.out.println("value" + value);
    }
  }



  @Test
  public void init() {
    Item item = new Item();
    Item item1 = new Item();
    Item item2 = new Item();
    Item item3 = new Item();
    item3.add("page3", true);
    item2.add("pop", true);
    item1.add("import", item2);
    item.add("page1", item3);
    item.add("page2", item1);

    System.out.println(JSON.toJSONString(item));


    /**
     * 2 p2 null
     * 3 import 2
     * 4 pop  3
     * 1 page1 null
     * 5 page3 1
     *
     *
     *
     *
     * val d = find
     * if (d !=null)
     * contains
     *
     * page2
     *   import
     *     pop: true
     *     dd:
     *
     *   upload_aaa false
     *
     * page1
     *   page3 true
     *
     * id function                  desc
     * 1  p1
     * 2  p1_import
     * 3  p1_clean
     * 4  delete
     * 5  p2
     * 6  p2_import
     * 7  p1_clean_top1
     * 8  p1_clean_top1_import
     *
     *
     * role1
     * p1 + p1_import
     *
     * [p1, p1_import]
     * {p1: true, p1_import:true}
     *
     *
     * if (page = 1) {
     *   render
     *   if (import) renrer butoton
     * }
     *
     * (A,b) -> Role1-> (p1, import)
     * (A,C) -> Role2 -> (clean, import, p2)
     */
  }
}