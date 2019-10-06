package com.tank.basic.tree;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * @author tank198435163.com
 */
public abstract class TreeNode {

  @Override
  public int hashCode() {
    return Objects.nonNull(id) ? Objects.hashCode(this.id) : super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (Objects.isNull(obj)) {
      return false;
    }
    boolean isTreeNode = obj instanceof TreeNode;
    if (!isTreeNode) {
      return false;
    }
    TreeNode node = (TreeNode) obj;
    return node.id.compareTo(this.getId()) == 0;
  }

  /**
   * just check current node is node or leaf
   *
   * @return
   */
  public abstract boolean isLeaf();
  
  @Getter
  @Setter
  public Long id;

  protected List<TreeNode> treeNodes = Lists.newLinkedList();
}
