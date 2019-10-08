package com.tank.basic.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class TreeNodeTest {

  @Before
  public void init() {
    master = new Master();
    master.setName("lb");
    master.id = 1L;

    t1 = new Teacher();
    t1.setName("gy");
    t1.id = 2L;

    t2 = new Teacher();
    t2.setName("zf");
    t2.id = 3L;
  }

  @Test
  public void addOneLevel() {
    master.add(t1);
    master.add(t2);
    master.print();
  }

  @Test
  public void addTwoLevel() {
    master.add(t1);
    master.add(t2);
    Student s1 = new Student();
    s1.setName("xb1");
    s1.id = 4L;
    t1.add(s1);
    master.print();
  }

  @Test
  public void testSearchNotExistsNode() {
    master.add(t1);
    master.add(t2);
    Optional<TreeNode> treeNodeOpt = master.search(100L);
    Assert.assertFalse(treeNodeOpt.isPresent());
  }

  @Test
  public void testSearchExistsNode() {
    master.add(t1);
    master.add(t2);
    Student s1 = new Student();
    s1.setName("xb1");
    s1.id = 4L;
    t1.add(s1);
    master.print();
    Optional<TreeNode> treeNodeOpt = master.search(s1.id);
    Assert.assertTrue(treeNodeOpt.isPresent());
  }


  private Master master = null;

  private Teacher t1 = null;

  private Teacher t2 = null;

  private AtomicLong seq = new AtomicLong();

}
