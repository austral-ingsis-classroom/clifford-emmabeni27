package edu.austral.ingsis.clifford;

import java.util.Optional;

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
    Optional<FileSystemComponent> toRemove =
        dir.getChildren().stream().filter(child -> child.getName().equals(name)).findFirst();

    if (!toRemove.isPresent()) {
      return "Element not found: " + name;
    }

    FileSystemComponent target = toRemove.get();

    if (target.isDirectory() && !recursive) {
      return "cannot remove '" + name + "', is a directory";
    }

    dir.removeChild(target);
    return "'" + name + "' removed";
  }
}
