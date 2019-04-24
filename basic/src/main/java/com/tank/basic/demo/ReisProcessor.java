package com.tank.basic.demo;

import java.util.Observable;
import java.util.Observer;

public class ReisProcessor implements Observer {

  @Override
  public void update(Observable o, Object arg) {

    System.out.println("redis processor:" + arg.toString());
  }
}