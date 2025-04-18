package edu.austral.ingsis.clifford;

public class Rm implements FileSystemOperation{

  String name;
  boolean recursive;

  public Rm(String name, boolean recursive){
    this.name = name;
    this.recursive = recursive;
  }

  @Override
  public void execute(FileSystemComponent currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      throw new RuntimeException("Current node is not a directory");
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
      throw new RuntimeException("Element not found: " + name);
    }

    if (toRemove.isDirectory()) {
      Directory dirToRemove = (Directory) toRemove;
      if (!recursive && !dirToRemove.getChildren().isEmpty()) {
        throw new RuntimeException("Cannot remove non-empty directory without --recursive flag");
      }
    }


    dir.removeChild(toRemove);
    System.out.println("'" + name + "' removed");
  }
}
