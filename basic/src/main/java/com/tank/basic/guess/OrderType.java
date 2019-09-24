package com.tank.basic.guess;

import lombok.NonNull;

/**
 * @author tank198435163.com
 */
public enum OrderType {

  POS(1, "pos订单"),
  ENJOY(2, "心享商城");

  OrderType(@NonNull Integer type, @NonNull String orderDesc) {
    this.type = type;
    this.desc = orderDesc;
  }

  public Integer type;

  public String desc;


}
