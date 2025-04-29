package edu.austral.ingsis.clifford;

import java.util.Optional;

public final class ResolvePath {

  // Private constructor to prevent instantiation
  private ResolvePath() {}

  public static FileSystemComponent resolvePath(String path, FileSystemComponent current) {
    if (path.equals(".")) return current;
    if (path.equals("..")) return current.getParent() != null ? current.getParent() : current;

    if (path.equals("/")) {
      while (current.getParent() != null) {
        current = current.getParent();
      }
      return current;
    }

    // ruta absoluta
    if (path.startsWith("/")) {
      while (current.getParent() != null) {
        current = current.getParent();
      }
      path = path.substring(1);
    }

    if (path.isEmpty()) return current;

    String[] parts = path.split("/");
    FileSystemComponent node = current;

    for (String part : parts) {
      if (!(node instanceof Directory)) return null;
      Optional<FileSystemComponent> found =
          ((Directory) node)
              .getChildren().stream().filter(child -> child.getName().equals(part)).findFirst();

      if (found.isEmpty()) return null;
      node = found.get();
    }

    return node;
  }
}
