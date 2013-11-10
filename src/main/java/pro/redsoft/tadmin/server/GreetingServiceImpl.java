package pro.redsoft.tadmin.server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import pro.redsoft.tadmin.client.GreetingService;
import pro.redsoft.tadmin.shared.Node;
import pro.redsoft.tadmin.shared.Tree;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

  File rootDir;
  Tree<String> resultTree;
  static Map<String, File> fileMap = new HashMap<String, File>();

  @Override
  public void init() throws ServletException {
    super.init();

    String path = System.getProperty("CATALINA_HOME");
    if (path == null) {
      path = System.getProperty("CATALINA_BASE");
    }
    if (path == null) {
      path = System.getProperty("user.dir");
    }

    rootDir = new File(path);

    assert rootDir.isDirectory();
    resultTree = new Tree<String>(rootDir.getName());
    fileMap.put(rootDir.getName(), rootDir);

    addFilesToNodes(rootDir, resultTree.getRoot());
  }

  @Override
  public Tree<String> getFileDescriptors() {
    return resultTree;
  }

  private static void addFilesToNodes(File target, Node<String> rootNode) {
    if (target.isDirectory()) {
      for (File file : target.listFiles()) {

        // process file
        Node<String> possibleRoot = new Node<String>(file.getName(), rootNode);
        rootNode.add(possibleRoot);
        fileMap.put(file.getName(), file);

        // process childs
        if (file.isDirectory()) {
          addFilesToNodes(file, possibleRoot);
        }
      }
    }
  }

  @Override
  public String readFile(Node<String> node) {
    File result = fileMap.get(node.getData());
    if (result.isDirectory()) {
      return result.getName();
    } else {
      return FileSystemUtils.readFile(result);
    }
  }
}
