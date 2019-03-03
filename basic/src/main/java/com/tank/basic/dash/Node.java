package com.tank.basic.dash;

import lombok.Data;

import java.util.Objects;

/**
 * @param <T>
 */
@Data
public class Node<T, R> {


  public Node<T, R> prev;

  public Node<T, R> next;

  private R data;

  @Override
  public String toString() {
    return Objects.isNull(data) ? "" : data.toString();
  }
}
