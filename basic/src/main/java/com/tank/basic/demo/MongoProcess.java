package com.tank.basic.demo;

import java.util.Observable;
import java.util.Observer;

public class MongoProcess implements Observer {
  @Override
  public void update(Observable o, Object arg) {
    System.out.println("mongo prcess:" + arg.toString());
  }
}
