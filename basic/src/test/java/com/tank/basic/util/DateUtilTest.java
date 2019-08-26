package com.tank.basic.util;

import com.tank.basic.common.DateUtil;
import org.junit.Test;

import java.util.Arrays;

public class DateUtilTest {

  @Test
  public void currentDateStrWithoutTime() {
    String str = DateUtil.currentDateStrWithoutTime();
    System.out.println(str);
  }

  @Test
  public void currentDateStrWithTime() {
    String str = DateUtil.currentDateStrWithTime();
    System.out.println(str);
  }


  @Test
  public void testCount() {
    long xx = Arrays.asList(1, 2, 3, 4).stream().filter(d -> d >= 2).filter(d -> d >= 1).count();
    System.out.println(xx);
  }
}