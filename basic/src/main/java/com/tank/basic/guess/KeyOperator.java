package com.tank.basic.guess;

import lombok.NonNull;

import java.util.List;

/**
 * @author tank198435163.com
 */
@FunctionalInterface
public interface KeyOperator {

  /**
   * handle redis key
   *
   * @param consumer
   * @param keys
   */
  void handleKey(@NonNull List<String> keys);
}
