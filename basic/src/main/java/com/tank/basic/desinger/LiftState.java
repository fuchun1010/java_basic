package com.tank.basic.desinger;

import lombok.Getter;
import lombok.Setter;

public abstract class LiftState {

  @Getter
  @Setter
  protected StateContext stateContext;

  protected abstract void open();

  protected abstract void running();

  protected abstract void close();

  protected abstract void stop();
}
