package com.tank.basic;

import com.tank.basic.service.AppConfig;
import com.tank.basic.service.impl.PropConfigImpl;
import com.typesafe.config.Config;

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
  }

}
