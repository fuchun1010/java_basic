package com.tank.basic.wheel;

import lombok.Data;

/**
 * @author tank198435163.com
 */
@Data
public abstract class Mission {

  private Integer delay;

  private String name;

  private Integer cycle;
}
