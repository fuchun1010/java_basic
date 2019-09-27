package com.tank.basic.desinger;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tank198435163.com
 */
public class StateContext {

  public void setLiftState(LiftState liftState) {
    this.liftState = liftState;
    this.liftState.setStateContext(this);
  }

  public void open() {
    this.liftState.open();
  }

  public static final OpenState openState = new OpenState();

  @Setter
  @Getter
  private LiftState liftState;
}
