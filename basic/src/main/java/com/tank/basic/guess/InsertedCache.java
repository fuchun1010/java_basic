package com.tank.basic.guess;

import lombok.NonNull;

/**
 * @author tank198435163.com
 */
public class InsertedCache extends CacheAccess {
  @Override
  protected String keyWrapper(@NonNull String key) {
    return String.format("i:%s", key);
  }
}
