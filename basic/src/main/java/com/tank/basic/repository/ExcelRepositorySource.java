package com.tank.basic.repository;

import com.google.common.collect.Lists;
import lombok.NonNull;

import java.util.List;

/**
 * @author tank198435163.com
 */
public class ExcelRepositorySource extends RepositorySource {

  public ExcelRepositorySource(@NonNull String type) {
    super(type);
  }

  @Override
  protected List<FruitDetail> parseData(@NonNull String path) {
    return Lists.newLinkedList();
  }

  @Override
  protected boolean isOk(@NonNull String path) {
    return false;
  }
}
