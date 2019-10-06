package com.tank.basic.tree;


import lombok.NonNull;

import java.util.Objects;
import java.util.Optional;

/**
 * @author tank198435163.com
 */
public class Node extends TreeNode {

  public boolean add(@NonNull final TreeNode node) {
    Optional<TreeNode> targetOpt = this.search(this.id);
    boolean isOk = targetOpt.
        flatMap(target -> Objects.isNull(target) ? Optional.of(this.treeNodes.add(node)) : Optional.of(target.treeNodes.add(node)))
        .orElse(false);
    return isOk;
  }


  public Optional<TreeNode> search(@NonNull Long id) {
    return this.search(this, id);
  }

  public void print() {
    System.out.println(this.toString());
    this.print(this);
  }

  private void print(@NonNull final TreeNode treeNode) {
    for (TreeNode node : treeNode.treeNodes) {
      System.out.println(node.toString());
      if (!node.isLeaf()) {
        print(node);
      }
    }
  }

  private Optional<TreeNode> search(@NonNull final TreeNode treeNode, @NonNull final Long id) {

    if (treeNode.getId().compareTo(id) == 0) {
      return Optional.of(treeNode);
    }

    for (TreeNode node : treeNode.treeNodes) {
      if (node.getId().compareTo(id) == 0) {
        return Optional.of(node);
      }
      if (!node.isLeaf()) {
        Optional<TreeNode> rsOpt = this.search(node, id);
        if (rsOpt.isPresent()) {
          return rsOpt;
        }
      }
    }
    return Optional.empty();
  }


  @Override
  public boolean isLeaf() {
    return this.treeNodes.isEmpty();
  }
}
