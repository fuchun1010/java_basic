package com.tank.basic.guess;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.NonNull;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

/**
 * @author tank198435163.com
 */
@Data
public abstract class Order implements Serializable {

  @Transient
  public boolean isEmptyCreateDateTime() {
    return !createDateTime.isPresent();
  }

  @Transient
  public boolean isEmptyCustomerCode() {
    return !customerCode.isPresent();
  }

  @Transient
  public boolean isEmptyItemList() {
    return items.isEmpty();
  }

  @Transient
  public void addItem(@NonNull final Item item) {
    this.items.add(item);
  }

  protected abstract int currentOrderType();

  private long id = 0L;

  private int payStatus = 0;

  private Optional<String> createDateTime = Optional.empty();

  private Optional<String> customerCode = Optional.empty();

  private Set<Item> items = Sets.newHashSet();


}
