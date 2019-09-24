package com.tank.basic.guess;

import lombok.NonNull;

import java.util.Observable;

/**
 * @author tank198435163.com
 */
public class Receiver<T, E extends T> extends Observable {

  public void receiveOrder(@NonNull final E item) {
    this.setChanged();
    this.notifyObservers(item);
  }


}
