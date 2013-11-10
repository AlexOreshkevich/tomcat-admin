package pro.redsoft.tadmin.client;

import pro.redsoft.tadmin.shared.Node;
import pro.redsoft.tadmin.shared.Tree;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {

  Tree<String> getFileDescriptors();

  String readFile(Node<String> node);
}
