package com.tank.basic.guess;

import lombok.NonNull;

/**
 * @author tank198435163.com
 */
public class OrderCache extends CacheAccess {
  @Override
  protected String keyWrapper(@NonNull String key) {
    return String.format("order:%s", key);
  }
}
