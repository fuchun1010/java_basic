package com.tank.basic.dynamic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

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


  @Test
  public void fetchLocalAddress() {
    try {
      InetAddress address = InetAddress.getLocalHost();
      System.out.println(address.getHostAddress());
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  public DataHandler dataHandler;
}