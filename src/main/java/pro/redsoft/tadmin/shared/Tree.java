package pro.redsoft.tadmin.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Tree<T extends Serializable> implements IsSerializable {

  private Node<T> root;

  public Tree() {
    this(null);
  }

  public Tree(T rootData) {
    root = new Node<T>(rootData, null);
  }

  public void addNode(Node<T> node) {
    root.add(node);
  }

  public void add(T data) {
    addNode(new Node<T>(data, root));
  }

  public Node<T> getRoot() {
    return root;
  }

  public void setRoot(Node<T> root) {
    this.root = root;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    // print root
    sb.append("[root] data=");
    sb.append(root.getData().toString());
    sb.append("\n ");

    // print childs
    for (Node<T> node : root.getChilds()) {
      sb.append(node.toString());
      sb.append(" ");
    }

    return sb.toString();
  }
}
