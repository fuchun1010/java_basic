package com.tank.basic.dynamic;

/**
 * @author fuchun
 */
@FunctionalInterface
public interface ReceiverHandler<T, E extends Exception> {

  void done(final T data) throws E;
}
