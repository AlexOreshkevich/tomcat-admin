package pro.redsoft.tadmin.client;

import java.io.Serializable;

import pro.redsoft.tadmin.shared.Node;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TomcatAdmin implements EntryPoint, SelectionHandler<TreeItem> {

  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

  private final ScrollPanel viewBox = new ScrollPanel();

  public static class NodeTreeItem<T extends Serializable> extends TreeItem {

    private final Node<T> node;

    public NodeTreeItem(Node<T> node) {
      super(new HTMLPanel(node.getData().toString()));
      this.node = node;
    }

    public Node<T> getNode() {
      return node;
    }
  }

  @Override
  public void onModuleLoad() {

    greetingService.getFileDescriptors(new AsyncCallback<pro.redsoft.tadmin.shared.Tree<String>>() {

      @Override
      public void onSuccess(pro.redsoft.tadmin.shared.Tree<String> result) {
        DockLayoutPanel root = new DockLayoutPanel(Unit.PX);

        // init north
        root.getElement().setAttribute("style", "border: 1px solid black;");
        root.addNorth(new HTMLPanel("<h2 style='text-align: center;'>Tomcat Admin Web Console</h2>"), 80);

        // init west
        Tree tree = new Tree();
        tree.addSelectionHandler(TomcatAdmin.this);
        tree.setAnimationEnabled(true);

        NodeTreeItem<String> rootItem = new NodeTreeItem<String>(result.getRoot());
        tree.addItem(rootItem);
        addTreeItem(rootItem, result.getRoot());

        rootItem.setState(true);

        ScrollPanel treeScroll = new ScrollPanel(tree);
        treeScroll.setAlwaysShowScrollBars(true);
        treeScroll.setHeight("100%");
        root.addWest(treeScroll, 400);

        tree.getElement().getStyle().setBorderWidth(1, Unit.PX);
        tree.getElement().getStyle().setBorderColor("black");
        tree.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);

        // init center
        root.add(viewBox);

        // load
        RootLayoutPanel.get().add(root);
      }

      @Override
      public void onFailure(Throwable caught) {
      }
    });
  }

  public static void addTreeItem(TreeItem rootItem, Node<String> rootNode) {
    if (!rootNode.getChilds().isEmpty()) {
      for (Node<String> node : rootNode.getChilds()) {

        NodeTreeItem<String> possibleRoot = new NodeTreeItem<String>(node);
        rootItem.addItem(possibleRoot);

        // start recursion
        if (!node.getChilds().isEmpty()) {
          addTreeItem(possibleRoot, node);
        }
      }
    }
  }

  @Override
  public void onSelection(SelectionEvent<TreeItem> event) {

    greetingService.readFile(((NodeTreeItem<String>) event.getSelectedItem()).getNode(), new AsyncCallback<String>() {

      @Override
      public void onFailure(Throwable caught) {
        show(caught.getLocalizedMessage());
        caught.printStackTrace();
      }

      @Override
      public void onSuccess(String result) {
        show(result);
      }
    });
  }

  private void show(String s) {
    viewBox.clear();
    if (s.contains("xml") || s.contains("html")) {
      viewBox.add(new HTMLPanel(s));
    } else {
      viewBox.add(new HTMLPanel("<pre>" + s + "</pre>"));
    }
    viewBox.setSize("94%", Window.getClientHeight() * 0.8 + "px");
    viewBox.getElement().getStyle().setPadding(20, Unit.PX);
    viewBox.setAlwaysShowScrollBars(true);
  }
}
