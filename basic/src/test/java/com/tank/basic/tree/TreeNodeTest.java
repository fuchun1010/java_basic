package com.tank.basic.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

public class TreeNodeTest {

  @Before
  public void init() {
    master = new Master();
    master.setName("lb");
    master.setId(1L);

    t1 = new Teacher();
    t1.setName("gy");
    t1.setId(2L);

    t2 = new Teacher();
    t2.setName("zf");
    t2.setId(3L);
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
    s1.setId(4L);

    t1.add(s1);

    master.print();

  }


  private Master master = null;

  private Teacher t1 = null;

  private Teacher t2 = null;

  private AtomicLong seq = new AtomicLong();

}
