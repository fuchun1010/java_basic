package com.tank.basic.repository;

/**
 * @author tank198435163.com
 */
public enum RepResourceType {

  EXCEL("excel", "excel data source"), ERP("http", "erp1");

  RepResourceType(String type, String desc) {
    this.type = type;
    this.desc = desc;
  }

  public String type;

  public String desc;
}
