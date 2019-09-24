package com.tank.basic.guess;

import lombok.NonNull;

import java.util.function.Function;

/**
 * @author tank198435163.com
 */
public class OrderPriceCalculator {

  public <T extends Order> double calculate(@NonNull final T order, Function<T, Double> fun) {
    return fun.apply(order);
  }
}
