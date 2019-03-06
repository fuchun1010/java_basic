package com.tank.util;

import com.typesafe.config.Config;

import java.util.Objects;

/**
 * @author fuchun
 */
public class ConfigKeyLoader {

  interface ConfigKeyValueOp<T> {

    T value(Config config, String key);
  }

  public static <T> T value(final String keyName, ConfigKeyValueOp<T> configKeyValueOp) {
    if (Objects.isNull(keyName)) {
      throw new NullPointerException("keyName not allowed null exception");
    }
    Config config = ConfigLoader.loadAppConfig();
    T result = configKeyValueOp.value(config, keyName);
    return result;
  }


  private ConfigKeyLoader() {

  }
}
