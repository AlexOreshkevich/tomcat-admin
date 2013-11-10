package pro.redsoft.tadmin.server;

import pro.redsoft.tadmin.shared.Node;
import pro.redsoft.tadmin.shared.Tree;

public class Test {

  public static void main(String[] args) {

    Tree<String> testTree = new Tree<String>("root");

    Node<String> node1 = new Node<String>("AAA", testTree.getRoot());
    Node<String> node2 = new Node<String>("BBB", testTree.getRoot());
    Node<String> node3 = new Node<String>("CCC", testTree.getRoot());

    testTree.addNode(node1);
    testTree.addNode(node2);
    testTree.addNode(node3);

    Node<String> node11 = new Node<String>("111", node2);
    Node<String> node12 = new Node<String>("222", node2);
    Node<String> node13 = new Node<String>("333", node2);

    node2.add(node11);
    node2.add(node12);
    node2.add(node13);

    System.out.println(testTree);

    assert testTree.getRoot().getLevel() == 0;
    assert node1.getLevel() == 1;
    assert node12.getLevel() == 2;
  }
}
