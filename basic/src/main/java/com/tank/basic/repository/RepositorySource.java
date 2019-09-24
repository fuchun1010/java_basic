package com.tank.basic.repository;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

/**
 * @author tank198435163.com
 */
public abstract class RepositorySource {

  public RepositorySource(@NonNull String type) {
    this.type = type;
  }

  /**
   * @param path
   * @return
   */
  protected abstract List<FruitDetail> parseData(@NonNull String path);


  public List<FruitDetail> loadFullData(String path) {
    if (this.isOk(path)) {
      return this.parseData(path);
    }

    return Lists.newLinkedList();
  }

  protected abstract boolean isOk(@NonNull String path);


  @Getter
  private String type;


}
