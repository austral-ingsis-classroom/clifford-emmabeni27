package edu.austral.ingsis.clifford;

import java.util.Optional;

public final class Rm implements Operation<String> {

  private final String name;
  private final boolean recursive;

  public Rm(String name, boolean recursive) {
    this.name = name;
    this.recursive = recursive;
  }

  @Override
  public Result execute(Component currentDirectory) {
    if (!currentDirectory.isDirectory()) {
      return new Error("Current node is not a directory");
    }

    Directory dir = (Directory) currentDirectory;
    Optional<Component> toRemove =
        dir.getChildren().stream().filter(child -> child.getName().equals(name)).findFirst();

    if (!toRemove.isPresent()) {
      return new Error("Element not found: " + name);
    }

    Component target = toRemove.get();

    if (target.isDirectory() && !recursive) {
      return new Error("cannot remove '" + name + "', is a directory");
    }

    dir.removeChild(target);
    return new Success("'" + name + "' removed");
  }
}
