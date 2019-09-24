package com.tank.basic.guess;

import lombok.Data;

import java.util.Objects;

/**
 * @author tank198435163.com
 */
@Data
public class Item {
  private String itemCode;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Item)) {
      return false;
    }
    Item item = (Item) o;
    return Objects.equals(getItemCode(), item.getItemCode());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getItemCode());
  }
}
