package com.tank.basic.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;

public class ApolloTest {

  @Before
  public void initApollo() {
    System.setProperty("app.id", "00001");
    System.setProperty("application", "booking-server");
    System.setProperty("apollo.meta", "http://localhost:18080");
    System.setProperty("apollo.cacheDir", this.cachePath());
    System.setProperty("env", "DEV");
    System.setProperty("apollo.configService", "http://localhost:18080");

  }

  @Test
  public void testConfig() {
    Config config = ConfigService.getAppConfig();
    String ip = config.getProperty("ip", "localhost");
    int port = config.getIntProperty("port", 0);
    int maxSize = config.getIntProperty("max-size", 0);
    System.out.println("--->" + ip);
    System.out.println("--->" + port);
    System.out.println("--->" + maxSize);
    Assert.assertTrue("192.168.0.101".equalsIgnoreCase(ip));
    Assert.assertTrue(port == 9002);
    
  }

  public String cachePath() {
    File file = new File("");
    String path = file.getAbsolutePath().replace("/basic", "/cache");
    return path;
  }
}
