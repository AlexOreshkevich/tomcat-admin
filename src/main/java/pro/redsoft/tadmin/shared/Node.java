package pro.redsoft.tadmin.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Node<T extends Serializable> implements IsSerializable {

  private List<Node<T>> childs;
  private T data;

  private int level;

  private Node<T> parent;

  public Node() {

  }

  public Node(T data, Node<T> parent) {
    this.data = data;
    this.parent = parent;
    childs = new ArrayList<Node<T>>();
    level = parent == null ? 0 : parent.level + 1;
  }

  public void add(Node<T> node) {
    childs.add(node);
  }

  public void add(T nodeData) {
    add(new Node<T>(nodeData, parent));
  }

  public String printChilds() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");

    for (int i = 0; i < level; i++) {
      sb.append("\t ");
    }

    for (Node<?> node : getChilds()) {
      sb.append(printNode(node));
    }

    sb.append("\n");

    return sb.toString();
  }

  public void setChilds(List<Node<T>> childs) {
    this.childs = childs;
  }

  public void setData(T data) {
    this.data = data;
  }

  public void setParent(Node<T> parent) {
    this.parent = parent;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(printNode(this));
    return sb.toString();
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public List<Node<T>> getChilds() {
    return childs;
  }

  public T getData() {
    return data;
  }

  public Node<T> getParent() {
    return parent;
  }

  public static String printNode(Node<?> node) {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append(node.getData().toString());
    sb.append("]");

    if (node.getChilds().size() > 0) {
      sb.append(node.printChilds());
    }

    return sb.toString();
  }
}
