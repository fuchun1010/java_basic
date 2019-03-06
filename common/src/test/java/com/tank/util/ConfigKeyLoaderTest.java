package com.tank.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigKeyLoaderTest {

  @Test
  public void serverNameValue() {
    String serverName = ConfigKeyLoader.<String>value("server.name", (config, key) -> config.getString(key));
    assertTrue("java_basic".equalsIgnoreCase(serverName));
    int port = ConfigKeyLoader.<Integer>value("server.port", ((config, key) -> config.getInt(key)));
    assertTrue(port > 0);
  }

}