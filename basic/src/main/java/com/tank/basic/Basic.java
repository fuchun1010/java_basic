package com.tank.basic;

import com.tank.basic.model.TableListenerModel;
import com.tank.basic.model.TableRules;
import com.tank.basic.service.AppConfig;
import com.tank.basic.service.impl.PropConfigImpl;
import com.typesafe.config.Config;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author fuchun
 */
public class Basic {
  public static void main(String[] args) {
    AppConfig appConfig = new PropConfigImpl();
    try {
      Config config = appConfig.loadConfig("config/mysql.conf");
      String url = config.getString("mysql.url");
      System.out.println("url = [" + url + "]");
    } catch (Exception e) {
      e.printStackTrace();
    }

    final String[] arr = new String[]{"hello", "welcome", "to", "china"};
    final String dbName = "demo";

    List<String> hashCodes = Arrays.stream(arr)
        .map(dbName::concat)
        .map(Objects::hashCode)
        .map(Math::abs)
        .map(code -> Math.floorMod(code, 10))
        .map(code -> "partition_" + code)
        .collect(Collectors.toList());

    hashCodes.forEach(System.out::println);

    TableRules tableRules = new TableRules();
    TableListenerModel regular = tableRules.recognizeModel("*");
    switch (regular) {
      case ALL: {
        System.out.println("* model");
        break;
      }
      case SINGLE: {
        System.out.println("sinle model");
        break;
      }
      case MULTI: {
        System.out.println("multi model");
      }
    }
  }

}
