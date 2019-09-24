package com.tank.basic.play;

import java.util.Comparator;
import java.util.List;

/**
 * @author tank198435163.com
 */
public class HumanSortHelper {

  public <T extends Human> List<T> sorted(List<T> humans, Comparator<T> human) {
    humans.sort(human);
    return humans;
  }
}
