package com.tank.basic.demo;

import javax.annotation.Nonnull;
import java.util.Observable;

public class WeatherObserver extends Observable {


  public void sendData(@Nonnull final Weather weather) {
    this.setChanged();
    this.notifyObservers(weather);
  }

}
