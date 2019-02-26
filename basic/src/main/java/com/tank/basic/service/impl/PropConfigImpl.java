package com.tank.basic.service.impl;

import com.tank.basic.service.AppConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class PropConfigImpl implements AppConfig {

  @Override
  public Config loadConfig(String filePath) throws FileNotFoundException {
    String absoluteConfigPath = this.userPath(filePath);

    File file = new File(absoluteConfigPath);
    if (!file.exists()) {
      throw new FileNotFoundException(absoluteConfigPath + "not exists");
    }

    return ConfigFactory.parseFile(file);
  }
}
