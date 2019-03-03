package com.tank.basic.dash;

import lombok.Data;

import java.util.Objects;

/**
 * 双节点链表
 * <p>
 * T: 节点类型
 * R: 节点的数据类型
 *
 * @param <T,R>
 */
@Data
public class DoubleLinkNode<T, R> {


  public DoubleLinkNode() {
    this.initDoubleLink();
  }


  public DoubleLinkNode addNode(final Node<T, R> newNode) {
    if (Objects.isNull(newNode)) {
      throw new NullPointerException("newNode not allowed null");
    }
    final Node<T, R> tailNode = this.findInsertPoint();

    final Node<T, R> preNode = tailNode.prev;

    newNode.next = tailNode;
    tailNode.prev = newNode;

    preNode.next = newNode;
    newNode.prev = preNode;

    return this;
  }

  public int size() {

    Node<T, R> current = this.head.next;

    int count = 0;

    for (; ; ) {
      if (Objects.isNull(current.next)) {
        break;
      } else {
        count++;
        current = current.next;
      }
    }

    return count;

  }

  public void print() {
    Node<T, R> currentNode = this.head;
    for (; ; ) {
      if (Objects.isNull(currentNode.next)) {
        break;
      } else {
        System.out.println(currentNode.toString());
        currentNode = currentNode.next;
      }
    }
  }

  /**
   * delete node with value
   *
   * @param value
   * @return
   */
  public DoubleLinkNode delete(final R value) {
    if (Objects.isNull(value)) {
      throw new NullPointerException("deleted value not allowed null");
    }
    Node<T, R> node = this.head;
    for (; ; ) {
      node = node.next;
      if (Objects.isNull(node)) {
        break;
      }
      boolean isEqual = value.equals(node.getData());
      if (isEqual) {
        //TODO reset pointer
        Node<T, R> nextNode = node.next;
        Node<T, R> preNode = node.prev;

        preNode.next = nextNode;
        nextNode.prev = preNode;

        node.prev = null;
        node.next = null;

        break;
      }
    }

    return this;
  }

  private Node<T, R> findInsertPoint() {
    return this.head.prev;
  }

  private void initDoubleLink() {
    this.head = new Node<>();
    this.tail = new Node<>();

    this.head.next = tail;
    this.head.prev = tail;

    this.tail.prev = this.head;
    this.tail.next = null;
  }

  private int index = -1;

  private Node<T, R> head = null;

  private Node<T, R> tail = null;
}
