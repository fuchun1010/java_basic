package com.tank.observer;

import java.util.Observable;

/**
 * @author
 */
public class DataSender extends Observable {


  public void send(final String data) {
    this.setChanged();
    this.notifyObservers(data);
  }

}
