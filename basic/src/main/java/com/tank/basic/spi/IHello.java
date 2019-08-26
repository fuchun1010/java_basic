package com.tank.basic.spi;

import lombok.NonNull;

/**
 * @author tank198435163.com
 */
public interface IHello {
  /**
   * @param name
   */
  void sayHello(@NonNull final String name);
}
