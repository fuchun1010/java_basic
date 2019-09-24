package com.tank.basic.guess;

import lombok.NonNull;

/**
 * @author tank198435163.com
 */
public class UpdatedCache extends CacheAccess {
  @Override
  protected String keyWrapper(@NonNull String key) {
    return String.format("updated:%s", key);
  }
}
