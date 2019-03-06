package com.tank.util;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author fuchun
 */
public class ConfigLoader {

  public static Config loadAppConfig() {
    return Single.INSTANCE.getAppConfig();
  }

  private enum Single implements ConfigDirPath {
    INSTANCE;

    Single() {
      try {
        File file = configFile("app.conf");
        config = ConfigFactory.parseFile(file);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }

    public Config getAppConfig() {
      return config;
    }

    private Config config;
  }

  private ConfigLoader() {

  }

}
