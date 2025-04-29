package edu.austral.ingsis.clifford;

import java.util.Optional;

public final class Rm implements FileSystemOperation<String>, CommandFactory {

  private final String name;
  private final boolean recursive;

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

  @Override
  public String commandName() {
    return "rm";
  }

  @Override
  public FileSystemOperation<String> fromParts(String[] parts) {
    if (parts.length < 2) {
      throw new IllegalArgumentException("File/Directory name required for 'rm' command");
    }

    boolean recursive = false;
    String name = null;

    for (int i = 1; i < parts.length; i++) {
      if ("--recursive".equals(parts[i])) {
        recursive = true;
      } else {
        name = parts[i];
      }
    }

    if (name == null) {
      throw new IllegalArgumentException("File/Directory name required for 'rm' command");
    }

    return new Rm(name, recursive);
  }
}