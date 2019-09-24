package com.tank.basic.guess;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * @author tank198435163.com
 */
public class PosOrderReceiver implements Observer {

  @Override
  public void update(Observable o, Object arg) {
    boolean isPosOrder = Objects.nonNull(arg) && arg instanceof PosOrder;
    if (!isPosOrder) {
      return;
    }
    PosOrder order = ((PosOrder) arg);
    OrderCache orderCache = new OrderCache();
    InsertedCache insertCache = new InsertedCache();
    String orderId = String.valueOf(order.getId());
    orderCache.handleKey(orderId, key -> {
      String insertKey = insertCache.keyWrapper(orderId);

      //TODO 1. create post order with hash constructure
      //TODO 2. tips insert action not executed
      //TODO 3. send message to mq async

    });
  }
}
