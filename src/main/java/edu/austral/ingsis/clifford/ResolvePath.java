package edu.austral.ingsis.clifford;

import java.util.Optional;

public class ResolvePath {

  static FileSystemComponent resolvePath(String path, FileSystemComponent current) {
    if (path.equals(".")) return current;
    if (path.equals("..")) return current.getParent() != null ? current.getParent() : current;

    // ruta absoluta
    if (path.startsWith("/")) {
      while (current.getParent() != null) {
        current = current.getParent();
      }
      path = path.substring(1);
    }

    String[] parts = path.split("/");
    FileSystemComponent node = current;

    for (String part : parts) {
      if (!(node instanceof Directory)) return null;
      Optional<FileSystemComponent> found = ((Directory) node).getChildren().stream()
        .filter(child -> child.getName().equals(part))
        .findFirst();

      if (found.isEmpty()) return null;
      node = found.get();
    }

    return node;
  }
}

