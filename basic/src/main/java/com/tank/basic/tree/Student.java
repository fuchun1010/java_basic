package com.tank.basic.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author tank198435163.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Student extends Node {

  @Override
  public String toString() {
    return String.format("node is student, id is:%d,name is:%s,leaf is:%s", id, name, this.isLeaf() ? "Y" : "N");
  }

  private String name;

}
