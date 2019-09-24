package com.tank.basic.repository;

import lombok.NonNull;

import java.util.List;

/**
 * @author tank198435163.com
 */
public class RepositoryHandler {

  public RepositoryHandler(@NonNull RepResourceType repResourceType) {
    String resourceType = repResourceType.type;
    if ("excel".equalsIgnoreCase(resourceType)) {
      this.repositorySource = new ExcelRepositorySource(resourceType);
    } else if ("http".equalsIgnoreCase(resourceType)) {
      this.repositorySource = new HttpRepositorySource(resourceType);
    }

  }

  public List<FruitDetail> read(@NonNull final String path) {
    return this.repositorySource.parseData(path);
  }

  private RepositorySource repositorySource;

}
