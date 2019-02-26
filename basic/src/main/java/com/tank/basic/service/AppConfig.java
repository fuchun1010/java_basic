package com.tank.basic.service;

import com.typesafe.config.Config;

import java.io.File;
import java.util.Map;

/**
 * @author fuchun
 */
public interface AppConfig {

  /**
   * @param filePath
   * @return
   */
  Config loadConfig(final String filePath) throws Exception;

  default String userPath(final String relativePath) {
    String userDir = System.getProperty("user.dir");
    return userDir + File.separator + relativePath;
  }

}
