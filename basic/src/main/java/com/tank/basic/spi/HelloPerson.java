package com.tank.basic.spi;

import lombok.NonNull;

/**
 * @author tank198435163.com
 */
public class HelloPerson implements IHello {

  @Override
  public void sayHello(@NonNull String name) {
    System.out.println(String.format("hello:%s", name));
  }

}
