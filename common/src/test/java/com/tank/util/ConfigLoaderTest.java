package com.tank.util;

import com.typesafe.config.Config;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigLoaderTest {

  @Before
  public void init() {
    this.config = ConfigLoader.loadAppConfig();
  }

  @Test
  public void testExistConfig() {
    assertNotNull(this.config);
  }

  @Test
  public void testExistKey() {
    String serverName = this.config.getString("server.name");
    assertNotNull(serverName);
    assertTrue("java_basic".equalsIgnoreCase(serverName));
  }

  private Config config = null;

}