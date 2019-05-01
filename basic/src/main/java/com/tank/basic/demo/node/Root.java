package com.tank.basic.demo.node;

import com.google.common.collect.Lists;
import lombok.NonNull;

import java.util.Objects;
import java.util.Optional;

/**
 * @author fuchun
 */
public class Root extends Node {


  public Root(Integer id, String desc, Integer parentId) {
    super(id, desc, parentId, Lists.newLinkedList());
  }


  public Root addNode(@NonNull final Node newNode) {

    boolean isHeaderNode = Objects.isNull(newNode.getParentId());

    if (this.isEmpty() || isHeaderNode) {
      this.getNodes().add(newNode);
    } else {
      Optional<Node> nodeOpt = this.searchParentNode(newNode.getParentId());

      if (nodeOpt.isPresent()) {
        Node node = nodeOpt.get();
        boolean isRootNode = node instanceof Root;
        if (isRootNode) {
          Root tmpRoot = ((Root) node);
          tmpRoot.getNodes().add(newNode);
        }

      }

    }

    return this;
  }

  public Optional<Node> searchParentNode(final Integer parentId) {


    boolean isEqual = this.getId().compareTo(parentId) == 0;

    if (isEqual) {

      return Optional.ofNullable(this);
    }

    for (Node node : this.getNodes()) {

      isEqual = node.getId().compareTo(parentId) == 0;

      if (isEqual) {
        return Optional.ofNullable(node);
      } else {

        boolean isRootNode = node instanceof Root;

        if (isRootNode) {
          Root tmpRoot = ((Root) node);
          Optional<Node> nodeOpt = tmpRoot.searchParentNode(parentId);
          if (nodeOpt.isPresent()) {
            return nodeOpt;
          }
        }
      }

    }

    return Optional.empty();
  }

  public void print() {
    System.out.println("id ---->" + this.getId());
    for (Node node : this.getNodes()) {
      boolean isRoot = node instanceof Root;
      if (isRoot) {
        Root tmpRoot = ((Root) node);
        tmpRoot.print();
      } else {
        System.out.println("id ----> " + node.getId());
      }
    }
  }

  public Optional<Node> search(final Integer id) {

    if (this.getId().compareTo(id) == 0) {
      return Optional.ofNullable(this);
    }

    for (Node node : this.getNodes()) {
      if (node.getId().compareTo(id) == 0) {
        return Optional.ofNullable(node);
      }

      boolean isRootNode = node instanceof Root;

      if (isRootNode) {
        Root tmpRoot = ((Root) node);
        Optional<Node> rs = tmpRoot.search(id);
        if (rs.isPresent()) {
          return rs;
        }
      }
    }


    return Optional.empty();
  }

  public boolean isEmpty() {
    return this.getNodes().size() == 0;
  }


  private Root p = this;
}
