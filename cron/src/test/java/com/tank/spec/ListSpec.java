package com.tank.spec;

import lombok.NonNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ListSpec {

  @Test
  public void testAccumulate() {
    List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    int rs = this.sum(data);
    Assert.assertTrue(rs > 0);
    System.out.println(rs);
  }


  private int sum(@NonNull final List<Integer> dataArr) {
    int s = 0;
    if (dataArr.size() > 5) {
      List<Integer> tmp = dataArr.subList(0, 4);
      List<Integer> remaining = dataArr.subList(4, dataArr.size());
      return sum(tmp) + sum(remaining);
    } else {
      s += dataArr.stream().reduce(0, Integer::sum);
    }

    return s;
  }
}
