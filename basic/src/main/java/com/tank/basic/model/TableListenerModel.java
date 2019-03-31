package com.tank.basic.model;

/**
 * @author fuchun
 */
public enum TableListenerModel {

  ALL(1), SINGLE(2), MULTI(3);

  private TableListenerModel(int type) {
    this.type = type;
  }

  private int type;
}
