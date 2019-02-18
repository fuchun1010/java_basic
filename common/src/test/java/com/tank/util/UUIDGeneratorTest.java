package com.tank.util;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UUIDGeneratorTest {

  @Test
  public void generateUUID() {

    val uuid = UUIDGenerator.generateUUID();
    Assert.assertTrue(new Long(uuid).compareTo(0L) > 0);
  }

  @Test
  public void init() {
    UUIDGenerator.init(2);
  }
}