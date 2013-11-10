package pro.redsoft.tadmin.client;

import pro.redsoft.tadmin.shared.Node;
import pro.redsoft.tadmin.shared.Tree;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GreetingServiceAsync {

  void getFileDescriptors(AsyncCallback<Tree<String>> callback);

  void readFile(Node<String> node, AsyncCallback<String> callback);
}
