package com.tank.basic.dynamic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataHandlerTest {

  @Before
  public void init() {
    this.dataHandler = new DataHandler();
  }

  @Test
  public void testDone() {
    String path = this.dataHandler.fetchProcessorPackagePath();
    Assert.assertTrue(path.equalsIgnoreCase("com.tank.basic.dynamic"));
  }

  @Test
  public void scanProcessor() {
    this.dataHandler.scanProcessor();
  }

  public DataHandler dataHandler;
}