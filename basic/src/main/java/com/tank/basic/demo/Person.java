package com.tank.basic.demo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Person {

  int id;
  String name;

  @Override
  public String toString() {

    return String.format("(%d,'%s')", id, name);
  }
}
