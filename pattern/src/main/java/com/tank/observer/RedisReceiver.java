package com.tank.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @author fuchun
 */
public class RedisReceiver implements Observer {

  @Override
  public void update(Observable o, Object arg) {
    System.out.println("redis = [" + o + "], arg = [" + arg + "]");
  }
}
