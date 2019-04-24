package com.tank.basic.demo;

import java.util.Observable;
import java.util.Observer;

public class KafkaProcessor implements Observer {
  @Override
  public void update(Observable o, Object arg) {
    Weather weather = ((Weather) arg);
    weather.setType("kafka");
    System.out.println("kafka process:" + arg.toString());
  }
}
