package com.tank.basic.guess;

import lombok.Data;

import java.beans.Transient;

/**
 * @author tank198435163.com
 */
@Data
public class PosOrder extends Order {

  @Transient
  @Override
  protected int currentOrderType() {
    return OrderType.POS.type;
  }
}
