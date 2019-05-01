package com.tank.basic.demo.node;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;

public class RootTest {


  @Test
  public void testSearch() {

  }

  @Test
  public void testPrint() {

  }

  @Test
  public void addNode() {
    System.out.println("start");
    this.root.addNode(b);
    this.root.addNode(c);
    this.root.addNode(d);
    String jsonStr = JSON.toJSONString(this.root);
    System.out.println("jsonStr = " + jsonStr);
  }

  @Test
  public void searchParentNode() {
  }

  @Test
  public void print() {
  }

  @Test
  public void search() {

  }

  @Before
  public void init() {
    this.root = new Root(1, "a".toUpperCase(), null);
  }

  private Node b = new Root(2, "b".toUpperCase(), 1);
  private Node c = new Root(3, "c".toUpperCase(), 1);
  private Node d = new Root(4, "d".toUpperCase(), 3);

  private Root root = null;
}