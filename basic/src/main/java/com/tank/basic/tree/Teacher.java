package com.tank.basic.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author tank198435163.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Teacher extends Node {

  @Override
  public String toString() {
    return String.format("node is teacher, id is:%d,name is:%s, isLeaf:%s", id, name, this.isLeaf() ? "Y" : "N");
  }

  private String name;
}
