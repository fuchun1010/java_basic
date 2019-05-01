package com.tank.basic.demo.node;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author fuchun
 */
@Data
@AllArgsConstructor
public abstract class Node {

  private Integer id;

  private String desc;

  private Integer parentId;

  private List<Node> nodes;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node node = (Node) o;
    return Objects.equals(id, node.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
