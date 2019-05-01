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
    this.root.addNode(e);
    this.root.addNode(f);
    this.root.addNode(g);
    String jsonStr = JSON.toJSONString(this.root);
    System.out.println("jsonStr = " + jsonStr);
    this.root.print();

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
    this.root = new Root(1, "root".toUpperCase(), null);
  }

  private Node b = new Root(2, "b".toUpperCase(), 1);
  private Node c = new Root(3, "c".toUpperCase(), 1);
  private Node d = new Root(4, "d".toUpperCase(), 3);
  private Node e = new Root(5, "e".toUpperCase(), 2);

  private Node f = new Root(8, "e".toUpperCase(), 6);

  private Node g = new Root(9, "f".toUpperCase(), null);

  private Root root = null;
}