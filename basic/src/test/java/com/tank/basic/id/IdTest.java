package com.tank.basic.id;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

public class IdTest {

  @Test
  public void testIdGenerate() {
    String id = ObjectId.get().toString();
    Assert.assertTrue(id.length() == 24);
    System.out.println(id);

  }

}
