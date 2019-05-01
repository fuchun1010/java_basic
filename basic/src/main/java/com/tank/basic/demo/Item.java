package com.tank.basic.demo;

import java.util.HashMap;

public class Item extends HashMap<String, Object> {

  public Item() {
    super();
  }

  public void add(String key, Object value) {
    this.putIfAbsent(key, value);
  }
}
