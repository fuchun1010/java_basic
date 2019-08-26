package com.tank.basic.spi;

import lombok.NonNull;

public class HelloTeacher implements IHello {

  @Override
  public void sayHello(@NonNull String name) {
    System.out.println(String.format("sir:%s", name));
  }
}
