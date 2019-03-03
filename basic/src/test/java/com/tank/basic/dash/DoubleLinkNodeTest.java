package com.tank.basic.dash;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.*;

public class DoubleLinkNodeTest {

  @Before
  public void init() {
    this.doubleLinkNode = new DoubleLinkNode<>();
  }

  @Test
  public void addNode() {
    Node<String, String> s1 = new Node<>();
    s1.setData("hello");
    Node<String, String> s2 = new Node<>();
    s2.setData("doubleLink");
    this.doubleLinkNode.addNode(s1);
    this.doubleLinkNode.addNode(s2);
    Assert.assertTrue(this.doubleLinkNode.size() == 2);
    //this.doubleLinkNode.print();
  }

  @Test
  public void print() {
    this.doubleLinkNode.print();
  }


  @Test
  public void delete() {
    this.addNode();
    this.print();
    System.out.println("======");
    this.doubleLinkNode.delete("hello");
    this.print();
    System.out.println("======");
  }

  @Test
  public void testHashFromData() {
    String[] arr = new String[]{"hello", "doubleLink", "fuchun"};
    Arrays.stream(arr).map(d -> Math.floorMod(Objects.hashCode(d), 10)).forEach(System.out::println);
  }

  private DoubleLinkNode<String, String> doubleLinkNode;


}