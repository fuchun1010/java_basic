package com.tank.util;

import lombok.val;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.io.File.separator;

/**
 * @author fuchun
 */
public interface ConfigDirPath {

  /**
   * @return
   */
  default File configFile(final String fileName) throws FileNotFoundException {

    String[] modulesName = new String[]{"common", "basic", "asyncdb"};
    List<String> subDirNames = Arrays.stream(modulesName).map(name -> separator + name).collect(Collectors.toList());
    String rootPath = System.getProperty("user.dir");

    for (String s : subDirNames) {
      rootPath = rootPath.replace(s, "");
    }
    val configPath = rootPath + separator + "config" + separator + fileName;
    File configFile = new File(configPath);

    if (configFile.exists()) {
      return configFile;
    } else {
      throw new FileNotFoundException(configPath + " not found");
    }
  }

}
