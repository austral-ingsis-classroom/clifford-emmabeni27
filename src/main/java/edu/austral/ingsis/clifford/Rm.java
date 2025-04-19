package edu.austral.ingsis.clifford;

public class Rm implements FileSystemOperation<String> {

  String name;
  boolean recursive;

  public Rm(String name, boolean recursive) {
    this.name = name;
    this.recursive = recursive;
  }

  @Override
  public String execute(FileSystemComponent currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return "Current node is not a directory";
    }

    Directory dir = (Directory) currentDirectory;
    FileSystemComponent toRemove = null;

    for (FileSystemComponent child : dir.getChildren()) {
      if (child.getName().equals(name)) {
        toRemove = child;
        break;
      }
    }

    if (toRemove == null) {
      return "Element not found: " + name;
    }

    if (toRemove.isDirectory()) {
      Directory dirToRemove = (Directory) toRemove;
      if (!recursive && !dirToRemove.getChildren().isEmpty()) {
        return "Cannot remove non-empty directory without --recursive flag";
      }
    }

    dir.removeChild(toRemove);
    return ("'" + name + "' removed");
  }
}
