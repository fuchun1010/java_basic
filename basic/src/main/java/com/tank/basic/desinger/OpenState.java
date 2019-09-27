package com.tank.basic.desinger;

/**
 * @author tank198435163.com
 */
public class OpenState extends LiftState {

  @Override
  protected void open() {
    super.stateContext.setLiftState(StateContext.openState);
    super.stateContext.getLiftState().open();
  }

  @Override
  protected void running() {
  }

  @Override
  protected void close() {

  }

  @Override
  protected void stop() {

  }
}
